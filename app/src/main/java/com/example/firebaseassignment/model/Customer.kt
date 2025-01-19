package com.example.firebaseassignment.model

data class Customer(
    val created_at: String,
    val device_type: String,
    val email: String,
    val email_verified_at: String,
    val fcm_token: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val status: Int,
    val token: String,
    val updated_at: String,
    val user_type: String
)