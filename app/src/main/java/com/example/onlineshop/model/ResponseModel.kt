package com.example.onlineshop.model

data class ResponseModel<T>(
    val success: Boolean,
    val data: T,
    val message: String,
    val error_code: Int
)