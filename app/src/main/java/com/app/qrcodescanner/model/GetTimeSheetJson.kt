package com.app.qrcodescanner.model

data class GetTimeSheetJson(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val break_time: BreakTime,
        val client: Client,
        val company: Company,
        val user: User,
        val work: Work
    ) {
        data class BreakTime(
            val format: String,
            val time: String
        )

        data class Client(
            val branch: Branch,
            val client_id: Int,
            val client_name: String,
            val unit: Unit
        ) {
            data class Branch(
                val id: Int,
                val name: String
            )

            data class Unit(
                val id: Int,
                val unit: String
            )
        }

        data class Company(
            val id: Int,
            val name: String
        )


        data class User(
            val email: String,
            val first_name: String,
            val id: Int,
            val last_name: String,
            val ni_number: String,
            val position: String
        )

        data class Work(
            val commencing_date: String,
            val date: String,
            val punch_in: String,
            val punch_out: String
        )
    }
}