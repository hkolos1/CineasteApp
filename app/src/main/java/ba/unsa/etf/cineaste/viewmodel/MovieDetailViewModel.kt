package ba.unsa.etf.cineaste.viewmodel

import ba.unsa.etf.cineaste.data.Movie
import ba.unsa.etf.cineaste.data.MovieRepository

class MovieDetailViewModel {

    fun getMovieByTitle(name:String): Movie {
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie= movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test")
    }

}