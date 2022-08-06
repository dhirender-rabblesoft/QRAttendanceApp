package com.app.qrcodescanner.model

data class FeedbackDetailJson(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val feedback: Feedback
    ) {
        data class Feedback(
            val feedback: Feedback,
            val feedback_options: ArrayList<FeedbackOption>
        ) {
            data class Feedback(
                val authorized_by: AuthorizedBy,
                val authorized_by_id: Int,
                val client: Client,
                val client_id: Int,
                val comment: String,
                val created_at: String,
                val deleted_at: Any,
                val feedback: String,
                val feedback_id: String,
                val id: Int,
                val rating: String,
                val signature: String,
                val updated_at: String,
                val user_id: Int
            ) {
                data class AuthorizedBy(
                    val company_id: Int,
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
            }

            data class FeedbackOption(
                val created_at: Any,
                val feedback: String,
                val id: Int,
                val updated_at: Any
            )
        }
    }
}