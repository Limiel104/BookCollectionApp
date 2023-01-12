package com.example.bookcollectionapp.book_feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookcollectionapp.book_feature.domain.model.Book

@Database(
    entities = [Book::class],
    version = 1
)
abstract class CollectionDatabase: RoomDatabase() {

    abstract val bookDao: BookDao

    companion object {
        const val DATABASE_NAME = "collection_db"
    }
}