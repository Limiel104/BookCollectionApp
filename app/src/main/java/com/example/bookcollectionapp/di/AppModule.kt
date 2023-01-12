package com.example.bookcollectionapp.di

import android.app.Application
import androidx.room.Room
import com.example.bookcollectionapp.book_feature.data.data_source.CollectionDatabase
import com.example.bookcollectionapp.book_feature.data.repository.BookRepositoryImpl
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository
import com.example.bookcollectionapp.book_feature.domain.use_case.AddBookUseCase
import com.example.bookcollectionapp.book_feature.domain.use_case.BookUseCases
import com.example.bookcollectionapp.book_feature.domain.use_case.DeleteBookUseCase
import com.example.bookcollectionapp.book_feature.domain.use_case.GetBooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCollectionDatabase(application: Application): CollectionDatabase {
        return Room.databaseBuilder(
            application,
            CollectionDatabase::class.java,
            CollectionDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookRepository(db: CollectionDatabase): BookRepository {
        return BookRepositoryImpl(db.bookDao)
    }

    @Provides
    @Singleton
    fun provideBookUseCases(repository: BookRepository): BookUseCases {
        return BookUseCases(
            getBooksUseCase = GetBooksUseCase(repository),
            deleteBookUseCase = DeleteBookUseCase(repository),
            addBookUseCase = AddBookUseCase(repository)
        )
    }
}