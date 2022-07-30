package com.app.qrcodescanner.model

data class CareListJson(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val client_id: Int,
        val created_at: String,
        val deleted_at: Any,
        val id: Int,
        val unit: String,
        val updated_at: String
    )
}