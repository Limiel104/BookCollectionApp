package com.example.bookcollectionapp.book_feature.domain.util

enum class Genre(
    val value: String
) {
    All("All"),
    Action("Action"),
    Adventure("Adventure"),
    Comedy("Comedy"),
    Fantasy("Fantasy"),
    Historical("Historical Fiction"),
    Horror("Horror"),
    Nonfiction("Nonfiction"),
    Mystery("Mystery"),
    Romance("Romance"),
    Science("Science Fiction"),
    Thriller("Thriller")
}

fun getAllGenres(): List<Genre> {
    return listOf(
        Genre.All,
        Genre.Action,
        Genre.Adventure,
        Genre.Comedy,
        Genre.Fantasy,
        Genre.Historical,
        Genre.Horror,
        Genre.Nonfiction,
        Genre.Mystery,
        Genre.Romance,
        Genre.Science,
        Genre.Thriller
    )
}

fun getAllGenresAsStrings(): List<String> {
    return listOf(
        Genre.Action.value,
        Genre.Adventure.value,
        Genre.Comedy.value,
        Genre.Fantasy.value,
        Genre.Historical.value,
        Genre.Horror.value,
        Genre.Nonfiction.value,
        Genre.Mystery.value,
        Genre.Romance.value,
        Genre.Science.value,
        Genre.Thriller.value
    )
}