package com.app.qrcodescanner.model

data class CilentListingModel(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val address: String,
        val created_at: String,
        val id: Int,
        val name: String,
        val post_code: String,
        val updated_at: String
    )
}