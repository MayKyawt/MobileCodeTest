package com.mkt.mobilecodetest.repository

import com.mkt.mobilecodetest.common.AppConstants.DEVELOPER_KEY
import com.mkt.mobilecodetest.data.FavModel
import com.mkt.mobilecodetest.network.MovieApi
import com.mkt.mobilecodetest.persistence.MovieDao
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val movieApi: MovieApi,
private val movieDao: MovieDao) {
    fun getMovieDetail(movieId:Int) = flow { emit(movieApi.getMovieDetails(movieId,DEVELOPER_KEY)) }
    fun getFav(movieId: Int) = flow { emit(movieDao.getFav(movieId)) }
    fun setFav(favModel: FavModel) = flow { emit(movieDao.setFav(favModel)) }
    fun isAlreadyExitInFavEntity(movieId: Int) = flow { emit(movieDao.exists(movieId)) }
}