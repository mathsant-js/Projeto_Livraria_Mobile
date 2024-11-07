package com.example.projeto_livraria_mobile.books.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Books::class], version = 1, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDAO

    companion object {
        @Volatile
        private var Instance: BooksDatabase? = null
        // if the Instance is not null, return it, otherwise create a new database instance.
        fun getDatabase(context: Context): BooksDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BooksDatabase::class.java, "item_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
