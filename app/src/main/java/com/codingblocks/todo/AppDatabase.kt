package com.codingblocks.todo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskModel::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}