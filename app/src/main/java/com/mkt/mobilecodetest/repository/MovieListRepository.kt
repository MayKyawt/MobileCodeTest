package com.mkt.mobilecodetest.repository

import com.mkt.mobilecodetest.common.AppConstants.DEFAULT_LANGUAGE
import com.mkt.mobilecodetest.common.AppConstants.DEVELOPER_KEY
import com.mkt.mobilecodetest.data.UpcomingMovies
import com.mkt.mobilecodetest.network.MovieApi
import com.mkt.mobilecodetest.persistence.MovieDao
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieListRepository @Inject constructor(private val movieApi: MovieApi
) {

    fun getUpcomingMovieList(page:Int) = flow { emit(movieApi.getUpcomingMovies(DEVELOPER_KEY,
        DEFAULT_LANGUAGE,page)) }
    fun getPopularMovieList(page:Int) = flow { emit(movieApi.getPopularMovies(DEVELOPER_KEY,
        DEFAULT_LANGUAGE,page)) }
}