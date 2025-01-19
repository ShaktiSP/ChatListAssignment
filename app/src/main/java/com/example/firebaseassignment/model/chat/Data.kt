package com.example.firebaseassignment.model.chat

data class Data(
    val booking_id: Int,
    val created_at: String,
    val created_at_human: String,
    val deleted_by: Any,
    val id: Int,
    val message: String,
    val receiver_id: Int,
    val sender_id: Int,
    val type: String,
    val updated_at: String,
    val updated_at_human: String
)