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

class ActorsFragment(movieName:String,movieId:Long?): Fragment() {
    private var movieName:String = movieName
    private var movieId:Long? = movieId
    private lateinit var movieRV:RecyclerView
    private var actorsList= listOf<String>()
    private lateinit var actorsRVSimpleAdapter:SimpleStringAdapter
    private var movieDetailViewModel =  MovieDetailViewModel(null,null, this@ActorsFragment::actorsRetrieved)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.actors_fragment, container, false)
        movieRV = view.findViewById<RecyclerView>(R.id.listActors)
        actorsList = movieName?.let { movieDetailViewModel.getActorsByTitle(it) }!!
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleAdapter = SimpleStringAdapter(actorsList)
        movieRV.adapter = actorsRVSimpleAdapter
        movieId?.let { movieDetailViewModel.getActorsById(it) }
        return view
    }

    fun actorsRetrieved(actors:MutableList<String>){
        actorsList=actors
        actorsRVSimpleAdapter.list=actors
        actorsRVSimpleAdapter.notifyDataSetChanged();
    }
}