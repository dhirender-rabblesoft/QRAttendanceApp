package com.app.qrcodescanner.model

data class AddTimeSheetJson(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val authorized_by_id: String,
        val branch_id: String,
        val client_id: String,
        val company_id: String,
        val created_at: String,
        val id: Int,
        val note: Any,
        val signature: String,
        val unit_id: String,
        val updated_at: String,
        val user_id: String
    )
}