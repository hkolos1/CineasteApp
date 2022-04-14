package ba.unsa.etf.cineaste


import ba.unsa.etf.cineaste.data.Movie
import ba.unsa.etf.cineaste.data.MovieRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.not
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.junit.Assert.assertEquals
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

//dodati testImplementation("org.hamcrest:hamcrest:2.2")
class RepositoryUnitTest {
    @Test
    fun testGetFavoriteMovies(){
        val movies = MovieRepository.getFavoriteMovies()
        assertEquals(movies.size,6)
        assertThat(movies, hasItem<Movie>(hasProperty("title", Is("Pulp Fiction"))))
        assertThat(movies, not(hasItem<Movie>(hasProperty("title", Is("Black Widow")))))
    }
}