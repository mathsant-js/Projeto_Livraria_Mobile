package com.example.projeto_livraria_mobile.books.data

import kotlinx.coroutines.flow.Flow

class OfflineBooksRepository(private val booksDAO: BooksDAO) : BooksRepository {
    override fun getAllBooksStream(): Flow<List<Books>> = booksDAO.getAllBooks()

    override fun getItemStream(id: Int): Flow<Books?> = booksDao.getItem(id)

    override suspend fun insertItem(item: Books) = booksDao.insert(item)

    override suspend fun deleteItem(item: Books) = booksDao.delete(item)

    override suspend fun updateItem(item: Books) = booksDao.update(item)
}