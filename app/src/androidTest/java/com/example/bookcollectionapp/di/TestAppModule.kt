package com.example.bookcollectionapp.di

import android.app.Application
import androidx.room.Room
import com.example.bookcollectionapp.book_feature.data.data_source.CollectionDatabase
import com.example.bookcollectionapp.book_feature.data.repository.BookRepositoryImpl
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository
import com.example.bookcollectionapp.book_feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideCollectionDatabase(application: Application): CollectionDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            CollectionDatabase::class.java,
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
            addBookUseCase = AddBookUseCase(repository),
            getBookUseCase = GetBookUseCase(repository),
            validateFieldInputUseCase = ValidateFieldInputUseCase()
        )
    }
}