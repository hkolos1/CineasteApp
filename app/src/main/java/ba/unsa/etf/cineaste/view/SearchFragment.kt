package ba.unsa.etf.cineaste.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.cineaste.MovieDetailActivity
import ba.unsa.etf.cineaste.R
import ba.unsa.etf.cineaste.data.Movie
import android.util.Pair
import ba.unsa.etf.cineaste.viewmodel.MovieListViewModel

class SearchFragment : Fragment() {

    private lateinit var searchText: EditText
    private lateinit var searchButton: AppCompatImageButton
    private lateinit var searchMovies: RecyclerView
    private lateinit var searchMoviesAdapter: MovieListAdapter
    private lateinit var movieListViewModel : MovieListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.search_fragment, container, false)
        searchText = view.findViewById(R.id.searchText)
        movieListViewModel = MovieListViewModel()
        searchButton = view.findViewById(R.id.searchButton)
        arguments?.getString("search")?.let {
            searchText.setText(it)
        }
        searchMovies = view.findViewById(R.id.searchList)
        searchMovies.layoutManager = GridLayoutManager(activity, 2)
        searchMoviesAdapter = MovieListAdapter(arrayListOf()) { movie,view1,view2 -> showMovieDetails(movie,view1,view2) }
        searchMovies.adapter=searchMoviesAdapter
        searchButton.setOnClickListener{
            onClick();
        }
        return view;
    }

    private fun onClick() {
        val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
        toast.show()
        movieListViewModel.search(searchText.text.toString(),onSuccess = ::onSuccess,
            onError = ::onError)
    }
    fun onSuccess(movies:List<Movie>){
        val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
        searchMoviesAdapter.updateMovies(movies)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showMovieDetails(movie: Movie, view1: View,view2:View) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie_id", movie.id)
        }
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity,  Pair.create(view1, "poster"),
                Pair.create(view2, "title"))
        startActivity(intent, options.toBundle())
    }
    companion object {
        fun newInstance(search:String): SearchFragment = SearchFragment().apply {
            arguments = Bundle().apply {
                putString("search", search)
            }
        }
    }
}