package com.example.assignment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.model.FavoriteGIFModel

@Dao
interface FavoriteGIFModelDao {
    @Query("SELECT * FROM favoriteGIFModel")
    fun getAll(): List<FavoriteGIFModel>

    @Insert
    fun insertGIF(favoriteGIFModel: FavoriteGIFModel)

    @Query("DELETE FROM favoriteGIFModel")
    fun deleteAll()

    @Query("DELETE FROM favoriteGIFModel WHERE id = :id")
    fun deleteItem(id: String)

    @Query("SELECT id FROM favoriteGIFModel WHERE id = :id")
    fun getId(id: String): String?

    @Query("SELECT `like` FROM favoriteGIFModel WHERE id = :id")
    fun getLike(id: String): String?

    @Query("SELECT url FROM favoriteGIFModel WHERE id = :id")
    fun getUrl(id: String): String?
}