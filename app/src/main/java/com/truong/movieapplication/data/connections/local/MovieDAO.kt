package com.truong.movieapplication.data.connections.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.truong.movieapplication.data.models.Movie

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Insert
    fun insertAll(vararg movies: Movie)

    @Query("DELETE FROM movies")
    fun deleteAll()
}