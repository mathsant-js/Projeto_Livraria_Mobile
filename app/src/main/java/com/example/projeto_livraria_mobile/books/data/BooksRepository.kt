package com.example.projeto_livraria_mobile.books.data

import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun getAllBooksStream(): Flow<List<Books>>
    /**
     * Retrieve a book from the given data source that matches with the [id].
     */
    fun getBookStream(id: Int): Flow<Books?>

    /**
     * Insert book in the data source
     */
    suspend fun insertBook(book: Books)

    /**
     * Delete book from the data source
     */
    suspend fun deleteBook(book: Books)

    /**
     * Update book in the data source
     */
    suspend fun updateBook(book: Books)
}