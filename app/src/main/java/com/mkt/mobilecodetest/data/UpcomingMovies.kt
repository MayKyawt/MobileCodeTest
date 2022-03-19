package com.mkt.mobilecodetest.data

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "upcoming_movie", primaryKeys = ["id"])
@Serializable
data class UpcomingMovies(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val poster_path: String?,
    @SerialName("overview")
    val overview: String,
    @SerialName("release_date")
    val release_date: String,
    @SerialName("vote_average")
    val rating: Double,
)