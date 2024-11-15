package com.example.projeto_livraria_mobile.books

import android.app.Application
import com.example.projeto_livraria_mobile.books.data.AppContainer
import com.example.projeto_livraria_mobile.books.data.AppDataContainer

class BooksApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}