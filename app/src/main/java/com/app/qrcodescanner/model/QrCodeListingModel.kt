package com.app.qrcodescanner.model

data class QrCodeListingModel(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val current_page: Int,
        val `data`: List<Data>,
        val first_page_url: String,
        val from: Int,
        val last_page: Int,
        val last_page_url: String,
        val links: List<Link>,
        val next_page_url: Any,
        val path: String,
        val per_page: Int,
        val prev_page_url: Any,
        val to: Int,
        val total: Int
    ) {
        data class Data(
            val client_id: Int,
            val created_at: String,
            val id: Int,
            val latitude: String,
            val longitude: String,
            val qrcode: String,
            val status: Int,
            val title: String,
            val updated_at: String
        )

        data class Link(
            val active: Boolean,
            val label: String,
            val url: Any
        )
    }
}