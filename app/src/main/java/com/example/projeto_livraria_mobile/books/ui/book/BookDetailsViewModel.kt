package com.example.projeto_livraria_mobile.books.ui.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_livraria_mobile.books.data.BooksRepository
import com.example.projeto_livraria_mobile.books.ui.book.BookDetailsScreen
import com.example.projeto_livraria_mobile.books.ui.book.BookDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailsDestination.bookIdArg])

    val uiState: StateFlow<BookDetailsUiState> =
        booksRepository.getBookStream(bookId)
            .filterNotNull()
            .map {
                BookDetailsUiState(outOfStock = it.quantity <= 0, bookDetails = it.toBookDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BookDetailsUiState()
            )

    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentBook = uiState.value.bookDetails.toBook()
            if (currentBook.quantity > 0) {
                booksRepository.updateBook(currentBook.copy(quantity = currentBook.quantity - 1))
            }
        }
    }

    suspend fun deleteBook() {
        booksRepository.deleteBook(uiState.value.bookDetails.toBook())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class BookDetailsUiState(
    val outOfStock: Boolean = true,
    val bookDetails: BookDetails = BookDetails()
)