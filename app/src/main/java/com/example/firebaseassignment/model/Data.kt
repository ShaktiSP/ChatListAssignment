package com.example.firebaseassignment.model

data class Data(
    val created_at: String,
    val customer: Customer,
    val description: String,
    val email: String,
    val id: Int,
    val image: String,
    val mobile: String,
    val name: String,
    val postal_code: String,
    val status: Int,
    val updated_at: String,
    val user_id: Int
)