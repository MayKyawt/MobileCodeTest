package com.mkt.mobilecodetest.data

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(tableName = "movieDetail",primaryKeys = ["Id"])
@Serializable
data class MovieDetailResponseModel (
    @SerialName("poster_path")
    val posterPath : String,
    @SerialName("adult")
    val adult : Boolean,
    @SerialName("overview")
    val overview : String,
    @SerialName("release_date")
    val releaseDate : String,
    @SerialName("id")
    val Id : Int,
    @SerialName("original_title")
    val originalTitle : String,
    @SerialName("original_language")
    val originalLanguage : String,
    @SerialName("title")
    val title : String,
    @SerialName("backdrop_path")
    val backdropPath : String,
    @SerialName("vote_count")
    val voteCount : Int,
    @SerialName("video")
    val isVideo : Boolean,
    @SerialName("runtime")
    val runtime : Int,
    @SerialName("vote_average")
    val rating : Double
)