package com.app.qrcodescanner.model

data class DecodeQr(
    val code: Int,
    val `data`: Data,
    val message: String
)
{
    data class Data(var lat:String,var long:String,var client:Client)
    {
        data class  Client(var id:String,var name:String)
    }
}
