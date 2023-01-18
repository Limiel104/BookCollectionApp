package com.example.bookcollectionapp.book_feature.domain.util

sealed class BookOrder() {
    class TitleAscending(): BookOrder()
    class TitleDescending(): BookOrder()
    class DateAscending(): BookOrder()
    class DateDescending(): BookOrder()
}