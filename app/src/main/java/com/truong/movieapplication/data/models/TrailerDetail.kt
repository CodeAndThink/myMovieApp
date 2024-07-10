package com.truong.movieapplication.data.models

import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrailerDetail(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val published_at: String
)
