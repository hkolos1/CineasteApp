package ba.unsa.etf.cineaste.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ba.unsa.etf.cineaste.BuildConfig
import ba.unsa.etf.cineaste.MovieDetailResultActivity
import ba.unsa.etf.cineaste.data.ApiAdapter
import ba.unsa.etf.cineaste.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class LatestMovieService : Service() {
    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false
    private val tmdb_api_key : String = BuildConfig.TMDB_API_KEY
    private var movie = Movie(1,"test","test","test","test","test","test")

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startService()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val notification = createNotification()
        startForeground(1, notification)
    }

    private fun startService() {
        if (isServiceStarted) return
        isServiceStarted = true

        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LatestMovieService::lock").apply {
                    acquire()
                }
            }

        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    getData()
                }
                delay(3600000)
            }
        }
    }


    private suspend fun getData() {
        try {
            var response = ApiAdapter.retrofit.getLatestMovie()
            val movie = response.body()
            val notifyIntent = Intent(this, MovieDetailResultActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("movie",movie)
            }
            val notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification = NotificationCompat.Builder(baseContext, "LATEST MOVIE SERVICE CHANNEL").apply{
                setSmallIcon(android.R.drawable.stat_notify_sync)
                setContentTitle("New movie found")
                setContentText(movie!!.title)
                setContentIntent(notifyPendingIntent)
                setOngoing(false)
                build()
            }
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(123, notification.build())
            }
        } catch (e: MalformedURLException) {
            Log.v("MalformedURLException", "Cannot open HttpURLConnection")
        } catch (e: IOException) {
            Log.v("IOException", "Cannot read stream")
        } catch (e: JSONException) {
            Log.v("IOException", "Cannot parse JSON")
        }
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "LATEST MOVIE SERVICE CHANNEL"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "Latest Movie notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Latest Movie Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }


        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return builder
            .setContentTitle("Finding latest film")
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .setTicker("Film")
            .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
            .build()
    }
}