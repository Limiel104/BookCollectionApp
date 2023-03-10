package com.example.bookcollectionapp.book_feature.data.repository

import com.example.bookcollectionapp.book_feature.data.data_source.BookDao
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val dao: BookDao
): BookRepository {

    override fun getBooks(): Flow<List<Book>> {
        return dao.getBooks()
    }

    override suspend fun getBookById(id: Int): Book? {
        return dao.getBookById(id)
    }

    override suspend fun insertBook(book: Book) {
        dao.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }

    override suspend fun searchBookList(query: String): List<Book> {
        return dao.searchBookList(query)
    }

    override suspend fun filterBookList(filter: String): List<Book> {
        return dao.filterBookList(filter)
    }

    override suspend fun searchAndFilterBookList(query: String, filter: String): List<Book> {
        return dao.searchAndFilterBookList(query,filter)
    }
}