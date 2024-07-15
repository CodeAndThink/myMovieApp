package com.truong.movieapplication.data.models

import android.os.Parcel
import android.os.Parcelable

data class MovieGenre(
    val id: Long,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieGenre> {
        override fun createFromParcel(parcel: Parcel): MovieGenre {
            return MovieGenre(parcel)
        }

        override fun newArray(size: Int): Array<MovieGenre?> {
            return arrayOfNulls(size)
        }
    }
}
