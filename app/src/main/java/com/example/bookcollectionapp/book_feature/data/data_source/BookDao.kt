package com.example.bookcollectionapp.book_feature.data.data_source

import androidx.room.*
import com.example.bookcollectionapp.book_feature.domain.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id: Int): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query(
        """
            SELECT *
            FROM book
            WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%'
            OR LOWER(author) LIKE '%' || LOWER(:query) || '%'
            OR LOWER(publisher) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchBookList(query: String): List<Book>

    @Query(
        """
            SELECT *
            FROM book
            WHERE genre LIKE :filter
        """
    )
    suspend fun filterBookList(filter: String): List<Book>

    @Query(
        """
            SELECT *
            FROM book
            WHERE genre LIKE :filter
            AND (LOWER(title) LIKE '%' || LOWER(:query) || '%'
            OR LOWER(author) LIKE '%' || LOWER(:query) || '%'
            OR LOWER(publisher) LIKE '%' || LOWER(:query) || '%')
        """
    )
    suspend fun searchAndFilterBookList(query: String, filter: String): List<Book>
}