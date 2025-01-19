package com.example.firebaseassignment.model

data class BookingResponse(
    val `data`: ArrayList<Data>,
    val message: String,
    val status: Boolean
)