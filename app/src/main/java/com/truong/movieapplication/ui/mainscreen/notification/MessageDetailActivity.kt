package com.truong.movieapplication.ui.mainscreen.notification

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.truong.movieapplication.databinding.ActivityMessageDetailBinding

class MessageDetailActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMessageDetailBinding
    private val binding get() = _binding
    private lateinit var title: String
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMessageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        if (intent.getStringExtra("title") != null && intent.getStringExtra("message") != null) {
            title = intent.getStringExtra("title")!!
            message = intent.getStringExtra("message")!!
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MessageDetailActivity()
    }
}