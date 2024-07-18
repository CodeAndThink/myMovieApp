package com.truong.movieapplication.data.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.truong.movieapplication.data.models.MovieGenre

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<Long>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Long>?): String? {
        if (list == null) {
            return null
        }
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromMovieGenreList(value: List<MovieGenre>?): String? {
        if (value == null) {
            return null
        }
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMovieGenreList(value: String?): List<MovieGenre>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<MovieGenre>>() {}.type
        return gson.fromJson(value, listType)
    }
}
