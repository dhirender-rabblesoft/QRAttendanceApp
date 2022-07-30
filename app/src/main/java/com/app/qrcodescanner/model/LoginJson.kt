package com.app.qrcodescanner.model

data class LoginJson(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val token: String,
        val user: User
    ) {
        data class User(
            val address: Any,
            val created_at: String,
            val date_of_birth: Any,
            val device_id: Any,
            val device_type: Any,
            val email: String,
            val email_verified_at: String,
            val first_name: String,
            val gender: Any,
            val id: Int,
            val company_id: Int,
            val image: Any,
            val last_name: String,
            val ni_number: Any,
            val payroll_number: Any,
            val phone_number: Any,
            val role: String,
            val shift_hours: Any,
            val updated_at: String
        )
    }
}