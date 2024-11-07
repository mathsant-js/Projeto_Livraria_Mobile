package com.example.projeto_livraria_mobile.books.data

import android.content.Context

interface AppContainer {
    val booksRepository: BooksRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineBooksRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [BooksRepository]
     */
    override val booksRepository: BooksRepository by lazy {
        OfflineBooksRepository(BooksDatabase.getDatabase(context).booksDao())
    }
}