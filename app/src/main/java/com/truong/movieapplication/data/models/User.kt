package com.truong.movieapplication.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "display_name")val display_name: String? = null,
    @ColumnInfo(name = "email")val email: String,
    @ColumnInfo(name = "password")val password: String,
    @ColumnInfo(name = "avatar")val avatar: String? = null,
    @ColumnInfo(name = "wish_list") var wish_list: List<Long>? = null,
    @ColumnInfo(name = "dob")val dob: String? = "2000-01-01",
    @ColumnInfo(name = "gender")val gender: Boolean? = true,
    @ColumnInfo(name = "address")val address: String? = null,
    @ColumnInfo(name = "phone")val phone: String? = null
)
