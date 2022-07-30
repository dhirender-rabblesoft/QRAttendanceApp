package com.app.qrcodescanner.model

data class AuthoriseList(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val company_id: Int,
        val created_at: String,
        val id: Int,
        val name: String,
        val updated_at: String
    )
}