package com.example.projeto_livraria_mobile.books.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Books(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val preco: Double,
    val quantity: Int
)