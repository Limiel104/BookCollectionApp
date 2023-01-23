package com.example.bookcollectionapp.book_feature.domain.util

enum class ReadingStatus(
    val value: String
) {
    Reading("Reading"),
    Completed("Completed")
}

fun getAllReadingStatuses(): List<String> {
    return listOf(
        ReadingStatus.Reading.value,
        ReadingStatus.Completed.value
    )
}