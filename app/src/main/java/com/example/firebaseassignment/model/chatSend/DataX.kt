package com.example.firebaseassignment.model.chatSend

data class DataX(
    val booking_id: String,
    val created_at: String,
    val created_at_human: String,
    val id: Int,
    val message: String,
    val receiver_id: Int,
    val sender_id: Int,
    val type: String,
    val updated_at: String,
    val updated_at_human: String
)