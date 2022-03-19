package com.mkt.mobilecodetest.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mkt.mobilecodetest.common.AppConstants.BASE_URL
import com.mkt.mobilecodetest.network.MovieApi
import com.mkt.mobilecodetest.persistence.MovieDao
import com.mkt.mobilecodetest.persistence.MovieDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit) : MovieApi{
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): MovieDb {
        return Room
            .databaseBuilder(app, MovieDb::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: MovieDb): MovieDao {
        return db.movieDao()
    }

}