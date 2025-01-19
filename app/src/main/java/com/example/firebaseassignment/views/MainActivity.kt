package com.example.firebaseassignment.views

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseassignment.adapter.BooingAdapter
import com.example.firebaseassignment.databinding.ActivityMainBinding
import com.example.firebaseassignment.model.Data
import com.example.firebaseassignment.network.Repository
import com.example.firebaseassignment.utils.Status
import com.example.firebaseassignment.viewModel.BookViewModel
import com.example.firebaseassignment.viewModel.BookViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var bookingAdapter: BooingAdapter
    private lateinit var binding: ActivityMainBinding
    private var list = ArrayList<Data>()

    private val postViewModel by viewModels<BookViewModel> {
        BookViewModelProvider(Application(), Repository())
    }

    private var currentPage = 1
    private val limit = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observers()
        postViewModel.bookingApi(currentPage, limit)
    }

    private fun setupRecyclerView() {
        binding.rvRecyclerView.layoutManager = LinearLayoutManager(this)
        bookingAdapter = BooingAdapter(this, onCallBackClick, list)
        binding.rvRecyclerView.adapter = bookingAdapter

        binding.rvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition + 1 >= totalItemCount) {
                    // Load next page
                    isLoading = true
                    currentPage++
                    postViewModel.bookingApi(currentPage, limit)
                }
            }
        })
    }

    private var onCallBackClick = object : BooingAdapter.OnClickType {
        override fun getType(
            userID: Int,
            name: String,
            email: String,
            mobile: String,
            postal_code: String,
            ImageUrl: String
        ) {
            val intent = Intent(this@MainActivity, ChatActivity::class.java)
            intent.putExtra("userID", userID)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("mobile", mobile)
            intent.putExtra("postal_code", postal_code)
            intent.putExtra("ImageUrl", ImageUrl)
            startActivity(intent)
        }
    }

    private fun observers() {
        postViewModel.postResponse.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    list.clear()
                    it.data?.let { data ->
                        list.addAll(data.data)
                        bookingAdapter.notifyDataSetChanged()

                        if (list.isNullOrEmpty()){
                         //   binding.progress.visibility = View.VISIBLE
                        } else {
                         //   binding.progress.visibility = View.GONE
                        }
                    }
                    isLoading = false
                //    binding.progressBar.visibility = View.GONE
                }

                Status.LOADING -> {
                    if (currentPage == 1) {
                //        binding.progressBar.visibility = View.GONE
                    } else {
                //        binding.progressBar.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    isLoading = false
                //    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}