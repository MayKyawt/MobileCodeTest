package com.mkt.mobilecodetest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkt.mobilecodetest.data.Resource
import com.mkt.mobilecodetest.data.MovieListResponseModel
import com.mkt.mobilecodetest.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieListRepository: MovieListRepository) :
    ViewModel() {
    private val _getPopularMovieList = MutableLiveData<Resource<MovieListResponseModel>>()
    private val _getUpcomingMovieList = MutableLiveData<Resource<MovieListResponseModel>>()
    val getPopularMovieList get() = _getPopularMovieList
    val getUpcomingMovieList get() = _getUpcomingMovieList
    var page = 0

    fun observePopularMovieList() {
        viewModelScope.launch {
            _getPopularMovieList.value = Resource.loading(null)
            movieListRepository.getPopularMovieList(page)
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _getPopularMovieList.value = Resource.error(e.localizedMessage, null)
                }
                .collect { result ->
                    _getPopularMovieList.value = Resource.success(result)
                }

        }
    }

    fun observeUpcomingMovieList() {
        viewModelScope.launch {
            _getUpcomingMovieList.value = Resource.loading(null)
            movieListRepository.getUpcomingMovieList(page)
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _getUpcomingMovieList.value = Resource.error(e.localizedMessage, null)
                }
                .collect { result ->
                    _getUpcomingMovieList.value = Resource.success(result)
                }

        }
    }
}
