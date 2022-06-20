package com.app.qrcodescanner.model

import java.io.Serializable

data class InvoiceListJson(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val client_id: Int,
        val created_at: String,
        val duration: Int,
        val end_date: Any,
        val id: Int,
        val invoice: String,
        val invoice_number: String,
        val no_days: Int,
        val no_hours: Double,
        val note: String,
        val price: Int,
        val start_date: Any,
        val status: Int,
        val total_amount: Int,
        val updated_at: String,
        val user_id: Int
    ):Serializable
}