package com.example.firebaseassignment.network

import com.example.firebaseassignment.model.BookingResponse
import com.example.firebaseassignment.model.chat.ChatListResponse
import com.example.firebaseassignment.model.chatSend.chateSendResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("booking/request/list")
    suspend fun bookList(@Query("page") page : Int, @Query("limit") limit: Int): Response<BookingResponse>

    @GET("chat/detail/{id}")
    suspend fun getChatList(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ChatListResponse>

    @FormUrlEncoded
    @POST("chat/store")
    suspend fun storeChatMessage(
        @Field("request_id") requestId: Int,
        @Field("message") message: String,
        @Field("type") type: String
    ): Response<chateSendResponse>
}