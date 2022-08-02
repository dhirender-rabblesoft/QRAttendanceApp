package com.app.qrcodescanner.model

data class FeebackListJson(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val created_at: Any,
        val feedback: String,
        val id: Int,
        var isfeedback:Boolean=true,
        val updated_at: Any
    )
}