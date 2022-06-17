package com.app.qrcodescanner.model

data class QrCodeGenerateModel(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val client_id: String,
        val created_at: String,
        val id: Int,
        val latitude: String,
        val longitude: String,
        val qrcode: String,
        val title: String,
        val updated_at: String
    )
}