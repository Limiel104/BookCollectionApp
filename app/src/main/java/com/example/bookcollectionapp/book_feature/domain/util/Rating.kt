package com.example.bookcollectionapp.book_feature.domain.util

enum class Rating(
    val value: String
) {
    One("1"),
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5")
}

fun getAllRatings(): List<String> {
    return listOf(
        Rating.One.value,
        Rating.Two.value,
        Rating.Three.value,
        Rating.Four.value,
        Rating.Five.value
    )
}