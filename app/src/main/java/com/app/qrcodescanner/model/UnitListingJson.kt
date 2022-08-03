package com.app.qrcodescanner.model

data class UnitListingJson(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val branches: List<Branche>,
        val units: List<Unit>
    ) {
        data class Branche(
            val client_id: Int,
            val created_at: String,
            val deleted_at: Any,
            val id: Int,
            val name: String,
            val updated_at: String
        )

        data class Unit(
            val client_id: Int,
            val created_at: String,
            val deleted_at: Any,
            val id: Int,
            val unit: String,
            val updated_at: String
        )
    }
}