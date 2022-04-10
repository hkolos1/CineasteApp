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

class SimilarFragment(movieName:String): Fragment() {
    private var movieName:String = movieName
    private var movieDetailViewModel =  MovieDetailViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.similar_fragment, container, false)
        var movieList = movieDetailViewModel.getSimilarByTitle(movieName)
        var movieRV = view.findViewById<RecyclerView>(R.id.listSimilar)
        movieRV.layoutManager = LinearLayoutManager(activity)
        var actorsRVSimpleAdapter = SimpleStringAdapter(movieList)
        movieRV.adapter = actorsRVSimpleAdapter
        return view
    }
}