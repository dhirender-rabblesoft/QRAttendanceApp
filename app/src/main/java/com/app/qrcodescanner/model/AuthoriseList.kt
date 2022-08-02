package com.app.qrcodescanner.model

data class AuthoriseList(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val company_id: Int=0,
        val created_at: String="",
        val id: Int=0,
        val name: String="",
        val updated_at: String=""
    )
}