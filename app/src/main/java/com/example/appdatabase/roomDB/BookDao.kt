package com.example.appdatabase.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Upsert
    suspend fun upsertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("UPDATE Book SET nome = :nome, autor = :autor, preco = :preco WHERE id = :id")
    fun updateBookQuery(nome: String, autor: String, preco: String, id: Int)

    @Query("DELETE FROM Book WHERE id = :id")
    fun deleteBookQuery(id: Int)

    @Query("SELECT * FROM Book")
    fun getAllBook(): Flow<List<Book>>
}