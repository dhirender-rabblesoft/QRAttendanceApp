package com.app.qrcodescanner.model

data class TimeSheetListJson(
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
            val authorized_by: AuthorizedBy,
            val authorized_by_id: Int,
            val branch: Branch,
            val branch_id: Int,
            val break_hours: String,
            val client: Client,
            val client_id: Int,
            val company: Company,
            val company_id: Int,
            val created_at: String,
            val deleted_at: Any,
            val id: Int,
            val is_copied: Int,
            val note: String,
            val punch_in: String,
            val punch_out: String,
            val signature: String,
            val timesheet: String,
            val unit: Unit,
            val unit_id: Int,
            val updated_at: String,
            val user_id: Int,
            val working_hours: String
        ) {
            data class AuthorizedBy(
                val company_id: Int,
                val created_at: String,
                val deleted_at: Any,
                val id: Int,
                val name: String,
                val updated_at: String
            )

            data class Branch(
                val client_id: Int,
                val created_at: String,
                val deleted_at: Any,
                val id: Int,
                val name: String,
                val updated_at: String
            )

            data class Client(
                val address: String,
                val address_2: Any,
                val company_id: Int,
                val created_at: String,
                val deleted_at: Any,
                val id: Int,
                val name: String,
                val post_code: String,
                val updated_at: String
            )

            data class Company(
                val address_line_1: String,
                val address_line_2: Any,
                val city: String,
                val created_at: String,
                val deleted_at: Any,
                val id: Int,
                val logo: Any,
                val name: String,
                val postcode: String,
                val status: Int,
                val updated_at: String,
                val user_id: Int
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

        data class Link(
            val active: Boolean,
            val label: String,
            val url: Any
        )
    }
}