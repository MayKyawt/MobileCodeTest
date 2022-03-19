package com.mkt.mobilecodetest.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "popular_movie", primaryKeys = ["id"])
@Serializable
data class PopularMovie(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val poster_path: String?,
    @SerialName("vote_average")
    val rating: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("release_date")
    val release_date: String
)