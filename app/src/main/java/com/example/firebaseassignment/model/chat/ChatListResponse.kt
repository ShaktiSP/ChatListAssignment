package com.example.firebaseassignment.model.chat

data class ChatListResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)