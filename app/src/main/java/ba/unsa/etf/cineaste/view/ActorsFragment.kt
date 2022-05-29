package ba.unsa.etf.cineaste.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.cineaste.R
import ba.unsa.etf.cineaste.data.Cast
import ba.unsa.etf.cineaste.viewmodel.MovieDetailViewModel

class ActorsFragment(movieName:String,movieId:Long?,favourite:Boolean): Fragment() {
    private val favourite = favourite
    private var movieName:String = movieName
    private var movieId:Long? = movieId
    private lateinit var movieRV:RecyclerView
    private var actorsList= listOf<Cast>()
    private lateinit var actorsRVSimpleSimilarAdapter:SimpleCastStringAdapter
    private var movieDetailViewModel =  MovieDetailViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.actors_fragment, container, false)
        movieRV = view.findViewById<RecyclerView>(R.id.listActors)
        //   actorsList = movieName?.let { movieDetailViewModel.getActorsByTitle(it) }!!
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleSimilarAdapter = SimpleCastStringAdapter(actorsList)
        movieRV.adapter = actorsRVSimpleSimilarAdapter
        if(favourite){
            movieId?.let { movieDetailViewModel.getActorsByIdDB(requireContext(),it,onSuccess = ::onSuccess,onError = ::onError) }
        }else{
            movieId?.let { movieDetailViewModel.getActorsById(it,onSuccess = ::onSuccess,
                onError = ::onError) }
        }

        return view
    }


    fun onSuccess(actors:List<Cast>){
        actorsList=actors
        actorsRVSimpleSimilarAdapter.list=actors
        actorsRVSimpleSimilarAdapter.notifyDataSetChanged();
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

}