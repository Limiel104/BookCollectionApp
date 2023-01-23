package com.example.bookcollectionapp.book_feature.domain.util

enum class Language(
    val value: String
) {
    Polish("Polish"),
    English("English")
}

fun getAllLanguages(): List<String> {
    return listOf(
        Language.Polish.value,
        Language.English.value
    )
}