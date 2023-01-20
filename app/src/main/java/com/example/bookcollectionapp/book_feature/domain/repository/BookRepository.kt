package com.example.bookcollectionapp.book_feature.domain.repository

import com.example.bookcollectionapp.book_feature.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(): Flow<List<Book>>

    suspend fun getBookById(id: Int): Book?

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    suspend fun searchBookList(query: String): List<Book>

    suspend fun filterBookList(filter: String): List<Book>

    suspend fun searchAndFilterBookList(query: String, filter: String): List<Book>
}