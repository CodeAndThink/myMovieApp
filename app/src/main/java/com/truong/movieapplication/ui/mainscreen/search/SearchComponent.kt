package com.truong.movieapplication.ui.mainscreen.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.movieapplication.adapters.SearchResultAdapter
import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.databinding.FragmentSearchComponentBinding
import com.truong.movieapplication.ui.mainscreen.MovieDetailActivity
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory

class SearchComponent : Fragment() {
    private lateinit var _binding: FragmentSearchComponentBinding
    private val binding get() = _binding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MainViewModelFactory(MovieRepository(ApiClients.dataInstance))
        mainViewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)
        val adapter = SearchResultAdapter()
        binding.searchResultListView.adapter = adapter
        binding.searchResultListView.layoutManager = LinearLayoutManager(requireActivity())

        binding.searchViewBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainViewModel.searchMovies(newText)
                }
                return true
            }
        })

        mainViewModel.searchResult.observe(requireActivity()) { movies ->
            adapter.submitList(movies)
            adapter.setOnClickListener(object : SearchResultAdapter.OnClickListener {
                override fun onClick(position: Int, movie: Movie) {
                    val openMovieDetailIntent = Intent(requireActivity(), MovieDetailActivity::class.java)
                    openMovieDetailIntent.putExtra("movie", movie)
                    openMovieDetailIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(openMovieDetailIntent)
                }
            })
        }

        mainViewModel.errorMessage.observe(requireActivity()) { message ->
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = SearchComponent()
    }
}