package com.example.appdatabase.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appdatabase.roomDB.Book
import kotlinx.coroutines.launch

class BookViewModel(private val repository: Repository) : ViewModel() {
    fun getBook() = repository.getAllBook().asLiveData(viewModelScope.coroutineContext)

    fun upsertBook(book: Book) {
        viewModelScope.launch {
            repository.upsertBook(book)
        }
    }

    fun updateBook(nome: String, autor: String, preco: String, id: String) {
        viewModelScope.launch {
            val book = Book(
                nome,
                autor,
                preco,
                id.toInt()
            )

            repository.upsertBook(book)
        }
    }

    fun deleteBook(nome: String, autor: String, preco: String, id: String) {
        viewModelScope.launch {
            val book = Book(
                nome,
                autor,
                preco,
                id.toInt()
            )

            repository.deleteBook(book)
        }
    }

    fun updateBookQuery(book: Book, id: Int) {
        viewModelScope.launch {
            repository.updateBookQuery(book, id)
        }
    }

    fun deleteBookQuery(id: Int) {
        viewModelScope.launch {
            repository.deleteBookQuery(id)
        }
    }
}