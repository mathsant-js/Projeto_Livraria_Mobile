package com.example.projeto_livraria_mobile.books.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projeto_livraria_mobile.books.BooksApplication
import com.example.projeto_livraria_mobile.books.ui.home.HomeViewModel
import com.example.projeto_livraria_mobile.books.ui.book.BookDetailsViewModel
import com.example.projeto_livraria_mobile.books.ui.book.BookEditViewModel
import com.example.projeto_livraria_mobile.books.ui.book.BookEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            BookEditViewModel(
                this.createSavedStateHandle(),
                BooksApplication().container.booksRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            BookEntryViewModel(BooksApplication().container.booksRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            BookDetailsViewModel(
                this.createSavedStateHandle(),
                BooksApplication().container.booksRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(BooksApplication().container.booksRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): BooksApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)