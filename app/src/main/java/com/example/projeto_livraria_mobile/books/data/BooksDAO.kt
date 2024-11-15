package com.example.projeto_livraria_mobile.books.data;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDAO {

    @Query("SELECT * FROM books ORDER BY name ASC")
    fun getAllBooks(): Flow<List<Books>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBook(id: Int): Flow<Books>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(books: Books);

    @Update
    suspend fun update(books: Books);

    @Delete
    suspend fun delete(books: Books);
}
