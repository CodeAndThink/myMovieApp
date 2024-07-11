package com.truong.movieapplication.ui.mainscreen.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.movieapplication.ui.adapters.MessageAdapter
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.respository.FirebaseService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.databinding.FragmentNotificationComponentBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory

class NotificationComponent : Fragment() {
    private lateinit var _binding: FragmentNotificationComponentBinding
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val factory = LoginViewModelFactory(LoginRepository(FirebaseService(), dao))
        loginViewModel = ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]

        loginViewModel.messages.observe(viewLifecycleOwner) { messages ->
            binding.messageList.adapter = MessageAdapter(messages)
        }

        binding.messageList.layoutManager = LinearLayoutManager(requireContext())

        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.fetchMessages()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationComponent()
    }
}