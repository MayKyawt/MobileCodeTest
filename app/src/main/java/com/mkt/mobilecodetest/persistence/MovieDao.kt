package com.mkt.mobilecodetest.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkt.mobilecodetest.data.FavModel
import com.mkt.mobilecodetest.data.MovieDetailResponseModel
import com.mkt.mobilecodetest.data.PopularMovie
import com.mkt.mobilecodetest.data.UpcomingMovies

@Dao
interface  MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertPopularMovies(repositories: List<PopularMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUpcomingMovies(repositories: List<UpcomingMovies>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertDetails(repositories: List<MovieDetailResponseModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFav(favModel: FavModel)

    @Query("SELECT * FROM popular_movie")
     fun getPopularMovies(): LiveData<List<PopularMovie>>

    @Query("SELECT * FROM upcoming_movie")
     fun getUpcomingMovies(): List<UpcomingMovies>

    @Query("SELECT * FROM movieDetail WHERE `id` = :movie_id")
     fun getDetails(movie_id: Int): List<MovieDetailResponseModel>

    @Query("SELECT EXISTS (SELECT * FROM favorite WHERE `id` = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("SELECT isFav FROM favorite WHERE `id` = :movie_id")
    suspend fun getFav(movie_id: Int):Boolean
}