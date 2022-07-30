package com.app.qrcodescanner.model

data class AddUnitJson(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val client_id: String,
        val created_at: String,
        val id: Int,
        val unit: String,
        val updated_at: String
    )
}