package ba.unsa.etf.cineaste.data

import ba.unsa.etf.cineaste.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object MovieRepository {

    private const val tmdb_api_key = BuildConfig.TMDB_API_KEY
    fun getFavoriteMovies() : List<Movie> {
        return favoriteMovies();
    }

    fun getRecentMovies() : List<Movie> {
        return recentMovies();
    }

    fun getSimilarMovies(): Map<String, List<String>> {
        return similarMovies()
    }


    suspend fun searchRequest(
        query: String
    ) : GetMoviesResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.searchMovie(query)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getSimilarMovies( id: Long
    ) : GetSimilarResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getSimilar(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }


    suspend fun getUpcomingMovies(
    ) : GetMoviesResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getUpcomingMovies()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getMovie(id: Long
    ) : Movie?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getMovie(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }


    fun getUpcomingMovies2(
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        ApiAdapter.retrofit.getUpcomingMovies2()
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

}