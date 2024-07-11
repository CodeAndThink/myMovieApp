package com.truong.truongnq20_androidviewclassic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.databinding.FragmentTopRateComponentBinding
import com.truong.movieapplication.ui.mainscreen.MovieDetailActivity
import com.truong.movieapplication.ui.adapters.TopRateMovieAdapter
import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory

class TopRateComponent : Fragment() {

    private lateinit var _binding: FragmentTopRateComponentBinding
    private val binding get() = _binding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRateComponentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MainViewModelFactory(MovieRepository(ApiClients.dataInstance))
        mainViewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        val adapter = TopRateMovieAdapter(emptyList())
        binding.topMovieRecycleView.adapter = adapter
        binding.topMovieRecycleView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        mainViewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            adapter.updateList(movies)
        }

        adapter.setOnClickListener(object :
            TopRateMovieAdapter.OnClickListener {
            override fun onClick(position: Int, movie: Movie) {
                val openMovieDetailIntent = Intent(requireActivity(), MovieDetailActivity::class.java)
                openMovieDetailIntent.putExtra("movie", movie)
                openMovieDetailIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(openMovieDetailIntent)
            }
        })
    }

    companion object {
        fun newInstance() = TopRateComponent()
    }
}