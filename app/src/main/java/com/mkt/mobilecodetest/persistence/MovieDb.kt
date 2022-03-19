package com.mkt.mobilecodetest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkt.mobilecodetest.data.FavModel
import com.mkt.mobilecodetest.data.MovieDetailResponseModel
import com.mkt.mobilecodetest.data.PopularMovie
import com.mkt.mobilecodetest.data.UpcomingMovies

@Database(
    entities = [
        PopularMovie::class, UpcomingMovies::class, MovieDetailResponseModel::class, FavModel::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}