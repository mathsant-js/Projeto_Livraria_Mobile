package com.example.projeto_livraria_mobile.books.data

import kotlinx.coroutines.flow.Flow

class OfflineBooksRepository(private val booksDAO: BooksDAO) : BooksRepository {
    override fun getAllBooksStream(): Flow<List<Books>> = booksDAO.getAllBooks()

    override fun getBookStream(id: Int): Flow<Books?> = booksDAO.getBook(id)

    override suspend fun insertBook(book: Books) = booksDAO.insert(book)

    override suspend fun deleteBook(book: Books) = booksDAO.delete(book)

    override suspend fun updateBook(book: Books) = booksDAO.update(book)
}