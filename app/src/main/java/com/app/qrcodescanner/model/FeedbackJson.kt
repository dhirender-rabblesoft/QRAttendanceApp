package com.app.qrcodescanner.model

data class FeedbackJson(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val client: Client,
        val user: User,
        val company: Company,
        val unit: Unit
    ) {
        data class Client(
            val address: String,
            val address_2: String,
            val client_name: String,
            val postcode: String
        )

        data class User(
            val date: Any,
            val first_name: String,
            val id: Int,
            val last_name: String,
            val position: Postions
        )
        {
            data class  Postions(var id:Int,var position:String,var  company_id:String)
        }
        data class Company(val company_id: Int,val company_name:String,val company_logo:String)
        data class Unit(val unit_id: Int,val unit:String)

    }
}