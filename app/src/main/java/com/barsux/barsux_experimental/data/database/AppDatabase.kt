package com.barsux.barsux_experimental.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barsux.barsux_experimental.data.database.dao.UserDao
import com.barsux.barsux_experimental.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}