package ba.unsa.etf.cineaste.viewmodel

import ba.unsa.etf.cineaste.data.GetMoviesResponse
import ba.unsa.etf.cineaste.data.Movie
import ba.unsa.etf.cineaste.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ba.unsa.etf.cineaste.data.Result


class MovieListViewModel(
) {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }

    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }

    fun search(query: String,onSuccess: (movies: List<Movie>) -> Unit,
               onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.searchRequest(query)

            // Display result of the network request to the user
            when (result) {
                is GetMoviesResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }

    fun getUpcoming( onSuccess: (movies: List<Movie>) -> Unit,
                     onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.getUpcomingMovies()

            // Display result of the network request to the user
            when (result) {
                is GetMoviesResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }

    fun getUpcoming2( onSuccess: (movies: List<Movie>) -> Unit,
                      onError: () -> Unit){
        MovieRepository.getUpcomingMovies2(onSuccess,onError)
    }

}