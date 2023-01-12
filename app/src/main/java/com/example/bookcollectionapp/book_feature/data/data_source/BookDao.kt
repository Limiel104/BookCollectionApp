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
}