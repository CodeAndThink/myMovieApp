package com.truong.movieapplication.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "genre_ids") val genre_ids: List<Long>? = null,
    @ColumnInfo(name = "genres") var genres: List<MovieGenre>? = null,
    @ColumnInfo(name = "poster_path") val poster_path: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "vote_average") val vote_average: Float,
    @ColumnInfo(name = "vote_count") val vote_count: Long,
    @ColumnInfo(name = "popularity") val popularity: Float,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String?,
    @ColumnInfo(name = "original_language") val original_language: String?,
    @ColumnInfo(name = "original_title") val original_title: String?,
    @ColumnInfo(name = "video") val video: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.createLongArray()?.toList(),
        mutableListOf<MovieGenre>().apply {
            parcel.readList(this, MovieGenre::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readLong(),
        parcel.readFloat(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(title)
        dest.writeLongArray(genre_ids?.toLongArray())
        dest.writeList(genres)
        dest.writeString(poster_path)
        dest.writeString(overview)
        dest.writeString(release_date)
        dest.writeFloat(vote_average)
        dest.writeLong(vote_count)
        dest.writeFloat(popularity)
        dest.writeByte(if (adult) 1.toByte() else 0.toByte())
        dest.writeString(backdrop_path)
        dest.writeString(original_language)
        dest.writeString(original_title)
        dest.writeByte(if (video) 1.toByte() else 0.toByte())
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(source: Parcel): Movie {
            return Movie(source)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
