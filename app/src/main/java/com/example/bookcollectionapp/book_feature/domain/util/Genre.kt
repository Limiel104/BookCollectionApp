package com.example.bookcollectionapp.book_feature.domain.util

enum class Genre(
    val value: String
) {
    ALL("All"),
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical Fiction"),
    HORROR("Horror"),
    NONFICTION("Nonfiction"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCIENCE("Science Fiction"),
    THRILLER("Thriller")
}

fun getAllGenres(): List<Genre> {
    return listOf(
        Genre.ALL,
        Genre.ACTION,
        Genre.ADVENTURE,
        Genre.COMEDY,
        Genre.FANTASY,
        Genre.HISTORICAL,
        Genre.HORROR,
        Genre.NONFICTION,
        Genre.MYSTERY,
        Genre.ROMANCE,
        Genre.SCIENCE,
        Genre.THRILLER
    )
}

fun getAllGenresAsStrings(): List<String> {
    return listOf(
        Genre.ACTION.value,
        Genre.ADVENTURE.value,
        Genre.COMEDY.value,
        Genre.FANTASY.value,
        Genre.HISTORICAL.value,
        Genre.HORROR.value,
        Genre.NONFICTION.value,
        Genre.MYSTERY.value,
        Genre.ROMANCE.value,
        Genre.SCIENCE.value,
        Genre.THRILLER.value
    )
}