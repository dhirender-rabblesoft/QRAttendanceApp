package com.app.qrcodescanner.model

data class FaqJson(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val answer: String,
        val created_at: String,
        val id: Int,
        val question: String,
        val updated_at: String
    )
}