package com.example.firebaseassignment.network

import com.example.firebaseassignment.model.BookingResponse
import com.example.firebaseassignment.model.chat.ChatListResponse
import com.example.firebaseassignment.model.chatSend.chateSendResponse
import retrofit2.Response

class Repository {

    suspend fun onBook(page: Int, limit: Int): Response<BookingResponse> {
        return RetrofitClient().getApi().bookList(page, limit)
    }

    suspend fun fetchChatDetails(id: Int, page: Int, limit: Int): Response<ChatListResponse> {
        return RetrofitClient().getApi().getChatList(id, page, limit)
    }

    suspend fun storeChatMessage(
        requestId: Int,
        message: String,
        type: String
    ): Response<chateSendResponse> {
        return RetrofitClient().getApi().storeChatMessage(requestId, message, type)
    }

}