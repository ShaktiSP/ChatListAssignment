package com.example.firebaseassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseassignment.model.BookingResponse
import com.example.firebaseassignment.model.chat.ChatListResponse
import com.example.firebaseassignment.model.chatSend.chateSendResponse
import com.example.firebaseassignment.network.Repository
import com.example.firebaseassignment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookViewModel(var repository: Repository) : ViewModel() {

    var postResponse = MutableLiveData<Resource<BookingResponse>>()
    var chatListResponse = MutableLiveData<Resource<ChatListResponse>>()
    var chatSendResponse = MutableLiveData<Resource<chateSendResponse>>()

    fun bookingApi(page : Int, limit: Int) {
        postResponse.value = Resource.loading(data = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.onBook(page, limit)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (response.code() == 200) {
                            postResponse.value = Resource.success(
                                data = responseBody!!,
                                message = responseBody.toString()
                            )
                        } else {
                            postResponse.value = Resource.error(
                                data = responseBody,
                                message = responseBody.toString()
                            )
                        }
                    } else {
                        postResponse.value = Resource.error(
                            data = null,
                            message = response.message()
                        )
                    }
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    postResponse.value = Resource.error(
                        data = null,
                        message = t.message.toString()
                    )
                }
            }
        }
    }

    fun chatApi(id: Int, page : Int, limit: Int) {
        chatListResponse.value = Resource.loading(data = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.fetchChatDetails(id, page, limit)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (response.code() == 200) {
                            chatListResponse.value = Resource.success(
                                data = responseBody!!,
                                message = responseBody.toString()
                            )
                        } else {
                            chatListResponse.value = Resource.error(
                                data = responseBody,
                                message = responseBody.toString()
                            )
                        }
                    } else {
                        chatListResponse.value = Resource.error(
                            data = null,
                            message = response.message()
                        )
                    }
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    chatListResponse.value = Resource.error(
                        data = null,
                        message = t.message.toString()
                    )
                }
            }
        }
    }

    fun chatSendApi(requestId: Int, message : String, type: String) {
        chatSendResponse.value = Resource.loading(data = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.storeChatMessage(requestId, message, type)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (response.code() == 200) {
                            chatSendResponse.value = Resource.success(
                                data = responseBody!!,
                                message = responseBody.toString()
                            )
                        } else {
                            chatSendResponse.value = Resource.error(
                                data = responseBody,
                                message = responseBody.toString()
                            )
                        }
                    } else {
                        chatSendResponse.value = Resource.error(
                            data = null,
                            message = response.message()
                        )
                    }
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    chatSendResponse.value = Resource.error(
                        data = null,
                        message = t.message.toString()
                    )
                }
            }
        }
    }

}