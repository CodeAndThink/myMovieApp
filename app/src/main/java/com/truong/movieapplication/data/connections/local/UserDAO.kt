package com.truong.movieapplication.data.connections.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.truong.movieapplication.data.models.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    fun getAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUser(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAllUser()

    @Delete
    fun deleteUser(user: User)
}