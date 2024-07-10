package com.truong.movieapplication.ui.mainscreen.profile.options

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.truong.movieapplication.adapters.TopRateMovieAdapter
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.respository.FirebaseAuthService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.databinding.FragmentWishListComponentBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory

class WishListComponent : Fragment() {
    private lateinit var _binding: FragmentWishListComponentBinding
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishListComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.getUserData(loginViewModel.getUserEmail()!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val loginFactory = LoginViewModelFactory(LoginRepository(FirebaseAuthService(), dao))
        loginViewModel = ViewModelProvider(requireActivity(), loginFactory)[LoginViewModel::class.java]

        val mainFactory = MainViewModelFactory(MovieRepository(ApiClients.dataInstance))
        mainViewModel = ViewModelProvider(requireActivity(), mainFactory)[MainViewModel::class.java]

        binding.backBtn.setOnClickListener {
            parentFragmentManager
                .popBackStack()
        }

        loginViewModel.user.observe(viewLifecycleOwner) {
            val user = it.getOrNull()
            if (user != null) {
                mainViewModel.getMoviesDetails(user.wish_list!!)
            }
        }

        mainViewModel.wishList.observe(viewLifecycleOwner) { movies ->
            val adapter = TopRateMovieAdapter(movies)
            binding.movieWishListRecyclerView.adapter = adapter
            binding.movieWishListRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WishListComponent()
    }
}