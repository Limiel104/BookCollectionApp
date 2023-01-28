package com.example.bookcollectionapp.book_feature.data.repository

import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBookRepository: BookRepository {

    private val books = mutableListOf<Book>()

    override fun getBooks(): Flow<List<Book>> {
        return flow{ emit(books) }
    }

    override suspend fun getBookById(id: Int): Book? {
        return books.find { it.id == id }
    }

    override suspend fun insertBook(book: Book) {
        books.add(book)
    }

    override suspend fun deleteBook(book: Book) {
        books.remove(book)
    }

    override suspend fun searchBookList(query: String): List<Book> {
        val searchResultList = mutableListOf<Book>()

        for(book in books) {
            val title = book.title.lowercase()
            val author = book.author.lowercase()
            val publisher = book.publisher.lowercase()
            if(title.contains(query.lowercase()) || author.contains(query.lowercase()) || publisher.contains(query.lowercase())) {
                searchResultList.add(book)
            }
        }
        return searchResultList
    }

    override suspend fun filterBookList(filter: String): List<Book> {
        val filterResultList = mutableListOf<Book>()

        for (book in books) {
            if(book.genre == filter) {
                filterResultList.add(book)
            }
        }
        return filterResultList
    }

    override suspend fun searchAndFilterBookList(query: String, filter: String): List<Book> {
        val searchAndFilterResultList = mutableListOf<Book>()

        for(book in books) {
            val title = book.title.lowercase()
            val author = book.author.lowercase()
            val publisher = book.publisher.lowercase()
            if(title.contains(query.lowercase()) || author.contains(query.lowercase()) || publisher.contains(query.lowercase())) {
                if(book.genre == filter) {
                    searchAndFilterResultList.add(book)
                }
            }
        }
        return searchAndFilterResultList
    }
}