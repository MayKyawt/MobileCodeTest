package com.mkt.mobilecodetest.data

import kotlinx.serialization.*

@Serializable
data class MovieListResponseModel (
    @SerialName("page")
    val page:Int,

    @SerialName("total_results")
    val total_results : Int,

    @SerialName("total_pages")
    val total_pages : Int,

    @SerialName("results")
    val results: List<MovieListInfoModel>
) : Pageable