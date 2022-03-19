package com.mkt.mobilecodetest.ui.movie_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkt.mobilecodetest.data.FavModel
import com.mkt.mobilecodetest.data.MovieDetailResponseModel
import com.mkt.mobilecodetest.data.Resource
import com.mkt.mobilecodetest.repository.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieDetailRepository: MovieDetailRepository): ViewModel() {
    private val _getMovieDetail = MutableLiveData<Resource<MovieDetailResponseModel>>()
    private val _getFav = MutableLiveData<Resource<Boolean>>()
    private val _setFav = MutableLiveData<Resource<Unit?>>()
    private val _isExist = MutableLiveData<Resource<Boolean>>()
    val getDetails get() = _getMovieDetail
    val getFav get() = _getFav
    val setFav get() = _setFav
    val isExit get() = _isExist
     var movieId :Int = 0

    fun observeMovieDetailsById(){
        viewModelScope.launch {
            _getMovieDetail.value = Resource.loading(null)
            movieDetailRepository.getMovieDetail(movieId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _getMovieDetail.value = Resource.error(e.localizedMessage,null)
                }
                .collect { result ->
                    _getMovieDetail.value = Resource.success(result
                    )
                }
        }
    }

    fun observeFav(){
        viewModelScope.launch {
            _getFav.value = Resource.loading(null)
            movieDetailRepository.getFav(movieId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _getFav.value = Resource.error(e.localizedMessage,null)
                }
                .collect { result ->
                    _getFav.value = Resource.success(result
                    )
                }
        }
    }
    fun observeExist(){
        viewModelScope.launch {
            _isExist.value = Resource.loading(null)
            movieDetailRepository.isAlreadyExitInFavEntity(movieId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _isExist.value = Resource.error(e.localizedMessage,null)
                }
                .collect { result ->
                    _isExist.value = Resource.success(result)
                }
        }
    }

    fun observeSetFav(favModel: FavModel){
        viewModelScope.launch {
            _setFav.value = Resource.loading(null)
            movieDetailRepository.setFav(favModel)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _setFav.value = Resource.error(e.localizedMessage,null)
                }
                .collect { result ->
                    _setFav.value = Resource.success(result)
                }
        }
    }

}