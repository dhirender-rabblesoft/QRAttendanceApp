package com.app.qrcodescanner.model

data class LoginJson2(
    val message: String,
    val token: String,
    val user: User
) {
    data class User(
        val age: String,
        val createdAt: String,
        val email: String,
        val id: Int,
        val name: String,
        val password: String,
        val updatedAt: String
    )
}