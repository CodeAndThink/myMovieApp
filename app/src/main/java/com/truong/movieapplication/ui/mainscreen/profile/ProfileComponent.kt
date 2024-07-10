package com.truong.movieapplication.ui.mainscreen.profile

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.movieapplication.R
import com.truong.movieapplication.adapters.ProfileOptionAdapter
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.respository.FirebaseAuthService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.databinding.FragmentProfileComponentBinding
import com.truong.movieapplication.ui.login.LoginActivity
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.mainscreen.profile.options.InforComponent
import com.truong.movieapplication.ui.mainscreen.profile.options.Settings
import com.truong.movieapplication.ui.mainscreen.profile.options.WishListComponent

class ProfileComponent : Fragment() {
    private lateinit var _binding: FragmentProfileComponentBinding
    private val binding get() = _binding
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val factory = LoginViewModelFactory(LoginRepository(FirebaseAuthService(), dao))
        loginViewModel = ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]

        val adapter = ProfileOptionAdapter(Settings.OPTIONS)
        binding.changeAvatarButton.setOnClickListener {
            loginViewModel.openGallery(requireActivity(), PICK_IMAGE_REQUEST)
        }

        loginViewModel.selectedImage.observe(viewLifecycleOwner) { bitmap ->
            binding.userAvatar.setImageBitmap(bitmap)
        }

        binding.profileOptionsList.adapter = adapter
        binding.profileOptionsList.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnClickListener(
            object : ProfileOptionAdapter.OnClickListener {
                override fun onClick(position: Int, option: Triple<Int, String, Int>) {
                    when(option.first) {
                        1 -> {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, InforComponent())
                                .addToBackStack(null)
                                .commit()
                        }
                        2 -> {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, WishListComponent())
                                .addToBackStack(null)
                                .commit()
                        }
                        3 -> {
                            val logoutIntent = Intent(requireContext(), LoginActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }

                            startActivity(logoutIntent)
                            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

                        }
                    }
                }
            }
        )
    }

    companion object {
        fun newInstance() = ProfileComponent()
    }
}