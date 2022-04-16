package com.example.assignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.dao.FavoriteGIFModelDao
import com.example.assignment.model.FavoriteGIFModel

@Database(entities = [FavoriteGIFModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteGIFModelDao(): FavoriteGIFModelDao
}