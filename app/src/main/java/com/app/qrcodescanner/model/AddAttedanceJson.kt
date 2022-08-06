package com.app.qrcodescanner.model

data class AddAttedanceJson(
    val break_hours: Any,
    val client_id: Int,
    val company_id: Int,
    val created_at: String,
    val id: Int,
    val punch_in: String,
    val punch_out: String,
    val qr_id: Int,
    val updated_at: String,
    val user_id: Int,
    val working_hours: String
)