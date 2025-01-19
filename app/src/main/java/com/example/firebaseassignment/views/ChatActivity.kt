package com.example.firebaseassignment.views

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.firebaseassignment.R
import com.example.firebaseassignment.adapter.SendMessageAdapter
import com.example.firebaseassignment.databinding.ActivityChatBinding
import com.example.firebaseassignment.model.chat.Data
import com.example.firebaseassignment.network.Repository
import com.example.firebaseassignment.utils.Constants
import com.example.firebaseassignment.utils.Status
import com.example.firebaseassignment.viewModel.BookViewModel
import com.example.firebaseassignment.viewModel.BookViewModelProvider

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var sendMessageAdapter: SendMessageAdapter
    private var chatList = ArrayList<Data>()
    private var userID = 0


    private val postViewModel by viewModels<BookViewModel> {
        BookViewModelProvider(Application(), Repository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userID = intent.getIntExtra("userID", 0)
        binding.tvName.text = intent.getStringExtra("name")
        binding.tvEmail.text = intent.getStringExtra("email")
        binding.tvMobile.text = intent.getStringExtra("mobile")
        binding.tvPostal.text = intent.getStringExtra("postal_code")
        Glide.with(this)
            .load(Constants.IMAGE_URL+intent.getStringExtra("ImageUrl"))
            .into(binding.sivImage)

        observers()
        postViewModel.chatApi(userID, 1,10)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.ivSend.setOnClickListener {
            postViewModel.chatSendApi(userID, binding.etChat.text.toString(), "text")
        }
    }

    private fun observers() {
        postViewModel.chatListResponse.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    chatList.clear()
                    chatList.addAll(it.data?.data!!)
                    sendMessageAdapter = SendMessageAdapter(this, chatList)
                    val layoutManager = LinearLayoutManager(this)
                    layoutManager.stackFromEnd = true
                    binding.rvRecyclerView.adapter = sendMessageAdapter

                    binding.rvRecyclerView.smoothScrollToPosition(chatList.size - 1)
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        postViewModel.chatSendResponse.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.etChat.setText("")
                    postViewModel.chatApi(userID, 1,10)
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}