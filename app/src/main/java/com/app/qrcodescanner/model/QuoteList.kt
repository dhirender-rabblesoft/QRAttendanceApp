package com.app.qrcodescanner.model

data class QuoteList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int
)
{
    data class Result(
        val _id: String,
        val author: String,
        val authorSlug: String,
        val content: String,
        val dateAdded: String,
        val dateModified: String,
        val length: Int,
        val tags: List<String>
    )
}
