package com.example.firebaseassignment.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseassignment.network.Repository

class BookViewModelProvider
    (val application: Application, private  val repository: Repository):
    ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(BookViewModel::class.java) -> {
                    BookViewModel(repository) as T
                }
                else -> throw  IllegalArgumentException("Class not found")
            }
        }

}