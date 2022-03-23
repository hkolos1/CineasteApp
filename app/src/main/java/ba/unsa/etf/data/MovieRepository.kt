package ba.unsa.etf.cineaste.data


object MovieRepository {

    fun getFavoriteMovies() : List<Movie> {
        return favoriteMovies();
    }

    fun getRecentMovies() : List<Movie> {
        return recentMovies();
    }
}