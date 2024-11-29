package com.example.appdatabase.viewModel

import com.example.appdatabase.roomDB.Book
import com.example.appdatabase.roomDB.BookDataBase

class Repository(private val db: BookDataBase) {
    suspend fun upsertBook(book: Book) {
        db.bookDao().upsertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        db.bookDao().deleteBook(book)
    }

    fun updateBookQuery(book: Book, id: Int) {
        val nome = book.nome
        val autor = book.autor
        val preco = book.preco
        db.bookDao().updateBookQuery(nome, autor, preco, id)
    }

    fun deleteBookQuery(id: Int) {
        db.bookDao().deleteBookQuery(id)
    }

    fun getAllBook() = db.bookDao().getAllBook()
}