package com.example.projeto_livraria_mobile.books.ui.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_livraria_mobile.books.data.BooksRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository
) : ViewModel() {
    var bookUiState by mutableStateOf(BookUiState())
        private set

    private val bookId: Int = checkNotNull(savedStateHandle[BookEditDestination.bookIdArg])

    init {
        viewModelScope.launch {
            bookUiState = booksRepository.getBookStream(bookId)
                .filterNotNull()
                .first()
                .toBookUiState(true)
        }
    }

    suspend fun updateBook() {
        if (validateInput(bookUiState.bookDetails)) {
            booksRepository.updateBook(bookUiState.bookDetails.toBook())
        }
    }

    fun updateUiState(bookDetails: BookDetails) {
        bookUiState =
            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
    }

    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && preco.isNotBlank() && quantity.isNotBlank()
        }
    }
}