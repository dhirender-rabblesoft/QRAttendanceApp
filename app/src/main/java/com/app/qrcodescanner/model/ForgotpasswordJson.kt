package com.app.qrcodescanner.model

data class ForgotpasswordJson(
    val code: Int,
    val `data`: List<Any>,
    val message: String
)