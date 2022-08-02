package com.app.qrcodescanner.model

data class CareListJson(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val client_id: Int=0,
        val created_at: String="",
        val deleted_at: Any=0,
        val id: Int=0,
        val unit: String="",
        val updated_at: String=""
    )
}