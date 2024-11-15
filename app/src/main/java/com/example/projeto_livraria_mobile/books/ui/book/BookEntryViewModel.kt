package com.example.projeto_livraria_mobile.books.ui.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projeto_livraria_mobile.books.data.Books
import com.example.projeto_livraria_mobile.books.data.BooksRepository
import java.text.NumberFormat

class BookEntryViewModel(private val booksRepository: BooksRepository) : ViewModel()  {
    var bookUiState by mutableStateOf(BookUiState())
        private set

    fun updateUiState(bookDetails: BookDetails) {
        bookUiState =
            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
    }

    suspend fun saveBook() {
        if (validateInput()) {
            booksRepository.insertBook(bookUiState.bookDetails.toBook())
        }
    }

    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && preco.isNotBlank() && quantity.isNotBlank()
        }
    }
}

data class BookUiState(
    val bookDetails: BookDetails = BookDetails(),
    val isEntryValid: Boolean = false
)

data class BookDetails(
    val id: Int = 0,
    val name: String = "",
    val preco: String = "",
    val quantity: String = "",
)

fun BookDetails.toBook(): Books = Books(
    id = id,
    name = name,
    preco = preco.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

fun Books.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(preco)
}

fun Books.toBookUiState(isEntryValid: Boolean = false): BookUiState = BookUiState(
    bookDetails = this.toBookDetails(),
    isEntryValid = isEntryValid
)

fun Books.toBookDetails(): BookDetails = BookDetails(
    id = id,
    name = name,
    preco = preco.toString(),
    quantity = quantity.toString()
)
