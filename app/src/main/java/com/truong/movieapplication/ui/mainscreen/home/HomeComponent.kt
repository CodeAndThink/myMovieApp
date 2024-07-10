package com.truong.movieapplication.ui.mainscreen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.truong.movieapplication.R
import com.truong.movieapplication.databinding.FragmentHomeComponentBinding
import com.truong.truongnq20_androidviewclassic.fragments.NowPlayingComponent
import com.truong.truongnq20_androidviewclassic.fragments.PopularMovieComponent
import com.truong.truongnq20_androidviewclassic.fragments.TopRateComponent
import com.truong.truongnq20_androidviewclassic.fragments.UpcomingMovieComponent

class HomeComponent : Fragment() {

    private lateinit var _binding: FragmentHomeComponentBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.beginTransaction()
            .add(binding.topRateMovieContainer.id, TopRateComponent())
            .commit()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(binding.popularMovieContainer.id, PopularMovieComponent())
            .commit()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(binding.upcomingMovieContainer.id, UpcomingMovieComponent())
            .commit()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(binding.nowPlayingMovieContainer.id, NowPlayingComponent())
            .commit()
    }

    companion object {
        fun newInstance() = HomeComponent()
    }
}