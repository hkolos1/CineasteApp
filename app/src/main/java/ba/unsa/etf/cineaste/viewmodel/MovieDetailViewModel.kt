package ba.unsa.etf.cineaste.viewmodel

import android.util.Log
import ba.unsa.etf.cineaste.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(

) {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getMovieByTitle(name:String):Movie{
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie= movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test","Test")
    }

    fun getActorsByTitle(name: String):List<String>{
        return ActorMovieRepository.getActorMovies()?.get(name)?: emptyList()
    }

    fun getSimilarByTitle(name: String):List<String>{
        return MovieRepository.getSimilarMovies()?.get(name)?: emptyList()
    }


    fun getSimilarMoviesById(query: Long, onSuccess: (movies: List<Movie>) -> Unit,
                             onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getSimilarMovies(query)
            when (result) {
                is GetSimilarResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }

    fun getActorsById(query: Long, onSuccess: (actors: List<Cast>) -> Unit,
                      onError: () -> Unit){
        scope.launch{
            val result = ActorMovieRepository.getCast(query)
            when (result) {
                is GetCastResponse -> onSuccess?.invoke(result.cast)
                else-> onError?.invoke()
            }
        }
    }


    fun getMovie(query: Long, onSuccess: (movies: Movie) -> Unit,
                 onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getMovie(query)
            when (result) {
                is Movie -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}