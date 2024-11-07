package com.example.projeto_livraria_mobile.books.data

import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun getAllBooksStream(): Flow<List<Books>>
    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Books?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Books)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Books)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Books)
}