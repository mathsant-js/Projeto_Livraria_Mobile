package com.example.appdatabase.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class],
    version = 1
)

abstract class BookDataBase : RoomDatabase() {
    abstract fun bookDao() : BookDao
}