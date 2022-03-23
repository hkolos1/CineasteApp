package ba.unsa.etf.cineaste.viewmodel

import ba.unsa.etf.cineaste.data.Movie
import ba.unsa.etf.cineaste.data.MovieRepository

class MovieListViewModel {

    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }

    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }
}