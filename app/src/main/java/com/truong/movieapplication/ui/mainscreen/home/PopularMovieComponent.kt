package com.truong.truongnq20_androidviewclassic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.databinding.FragmentPopularMovieComponentBinding
import com.truong.movieapplication.ui.mainscreen.MovieDetailActivity
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory
import com.truong.movieapplication.adapters.PopularMovieAdapter

class PopularMovieComponent : Fragment() {
    private lateinit var _binding: FragmentPopularMovieComponentBinding
    private val binding get() = _binding
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: PopularMovieAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMovieComponentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MainViewModelFactory(MovieRepository(ApiClients.dataInstance))
        mainViewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)
        mainViewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            adapter = PopularMovieAdapter(movies, binding.popularMovieRecycleView)
            viewPager2 = binding.popularMovieRecycleView
            binding.popularMovieRecycleView.adapter = adapter
            viewPager2.currentItem = 1
            createInfinityScroll(movies.size + 2)
        }
    }

    private fun createInfinityScroll(listSize: Int) {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    when (viewPager2.currentItem) {
                        listSize - 1 -> viewPager2.setCurrentItem(1, false)
                        0 -> viewPager2.setCurrentItem(listSize - 2, false)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position != 0 && position != listSize - 1) {
                    adapter.setOnClickListener(object :
                        PopularMovieAdapter.OnClickListener {
                        override fun onClick(position: Int, movie: Movie) {
                            val openMovieDetailIntent = Intent(requireActivity(), MovieDetailActivity::class.java)
                            openMovieDetailIntent.putExtra("movie", movie)
                            openMovieDetailIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                            startActivity(openMovieDetailIntent)
                        }
                    })
                }
            }
        })
    }

    companion object {
        fun newInstance() = PopularMovieComponent()
    }
}