package ba.unsa.etf.cineaste.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.cineaste.R
import ba.unsa.etf.cineaste.viewmodel.MovieDetailViewModel

class SimilarFragment(movieName:String,movieId:Long?): Fragment() {
    private var movieName:String = movieName
    private var movieId:Long? = movieId
    private lateinit var movieRV:RecyclerView
    private var movieList= listOf<String>()
    private lateinit var actorsRVSimpleAdapter:SimpleStringAdapter
    private var movieDetailViewModel =  MovieDetailViewModel(null,this@SimilarFragment::similarRetrieved,null)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.similar_fragment, container, false)
        movieRV = view.findViewById<RecyclerView>(R.id.listSimilar)
        movieList = movieName?.let { movieDetailViewModel.getSimilarByTitle(it) }!!
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleAdapter = SimpleStringAdapter(movieList)
        movieRV.adapter = actorsRVSimpleAdapter
        movieId?.let { movieDetailViewModel.getSimilarMoviesById(it) }
        return view
    }
    fun similarRetrieved(similar:MutableList<String>){
        movieList=similar
        actorsRVSimpleAdapter.list = similar;
        actorsRVSimpleAdapter.notifyDataSetChanged();
    }
}