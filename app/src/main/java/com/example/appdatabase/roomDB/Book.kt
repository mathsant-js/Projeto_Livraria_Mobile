package com.example.appdatabase.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    val nome: String,
    val autor: String,
    val preco: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
