package com.mkt.mobilecodetest.ui.movie_detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mkt.mobilecodetest.R
import com.mkt.mobilecodetest.common.AppConstants
import com.mkt.mobilecodetest.common.BaseViewBinding
import com.mkt.mobilecodetest.common.BlurImage
import com.mkt.mobilecodetest.data.DataState
import com.mkt.mobilecodetest.data.FavModel
import com.mkt.mobilecodetest.databinding.ActivityMovieDetailBinding
import com.mkt.mobilecodetest.persistence.MovieDb
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : BaseViewBinding() {
    @Inject
    lateinit var movieDb: MovieDb

    lateinit var binding: ActivityMovieDetailBinding

    lateinit var viewModel: MovieDetailViewModel

    private var isFavorite: Boolean = false
    private var isAlreadyExit: Boolean = false

    companion object {
        private const val IE_ID = "movie_id"
        fun getMovieDetailIntent(context: Context, movieID: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(IE_ID, movieID)
            return intent
        }
    }

    override fun getView(): View {
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUpContents(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        viewModel.movieId = intent.getIntExtra(IE_ID, 0)

        observeMovieDetails()
        getMovieDetails()
        observeIsAlreadyExit()
        viewModel.observeExist()

        binding.ivFav.setOnClickListener {
            observeSetFav()
            setFav()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.swipeToRefresh.setOnRefreshListener{
            binding.swipeToRefresh.isRefreshing = false
            getMovieDetails()
        }
    }

    private fun setFav() {
        val favModel = FavModel()
        favModel.isFav = !isFavorite
        favModel.id = viewModel.movieId
        viewModel.observeSetFav(favModel)
    }

    private fun observeSetFav() {
        viewModel.setFav.observe(this, Observer {
            when (it.status) {
                DataState.LOADING -> {

                }
                DataState.SUCCESS -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    observeFav()
                    getFav()
                }
                DataState.ERROR -> {

                }
                DataState.DUPLICATE -> {

                }
            }
        })
    }

    private fun observeIsAlreadyExit() {
        viewModel.isExit.observe(this, Observer {
            when (it.status) {
                DataState.LOADING -> {

                }
                DataState.SUCCESS -> {
                    isAlreadyExit = it.data == true
                    if (isAlreadyExit){
                        observeFav()
                        getFav()
                    }
                }
                DataState.ERROR -> {

                }
                DataState.DUPLICATE -> {
                }
            }
        })
    }

    private fun getFav() {
        viewModel.observeFav()
    }

    private fun observeFav() {
        viewModel.getFav.observe(this, Observer {
            when (it.status) {
                DataState.LOADING -> {

                }
                DataState.SUCCESS -> {
                    if (it.data == true) {
                        isFavorite = true
                        Glide.with(this@MovieDetailActivity)
                            .load(R.drawable.heart_filled_selected)
                            .into(binding.ivFav)
                    } else {
                        isFavorite = false
                        Glide.with(this@MovieDetailActivity)
                            .load(R.drawable.heartfilled)
                            .into(binding.ivFav)
                    }
                }
                DataState.ERROR -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                DataState.DUPLICATE -> {
                }
            }
        })

    }

    private fun getMovieDetails() {
        viewModel.observeMovieDetailsById()
    }

    private fun observeMovieDetails() {
        viewModel.getDetails.observe(this, Observer { result ->
            when (result.status) {
                DataState.LOADING -> {
                    showDialog()
                }
                DataState.SUCCESS -> {
                    hideDialog()
                    Glide.with(this)
                        .load(AppConstants.BASE_IMG_URL + result.data?.posterPath)
                        .into(binding.ivMoviePoster)
                    val bitmapImage: Bitmap = BlurImage.fastblur(BitmapFactory.decodeResource(
                        resources, R.drawable.img_placeholder),
                        0.08.toFloat(), 5)

                    Glide.with(this@MovieDetailActivity)
                        .load(bitmapImage)
                        .into(binding.ivBg)
                    binding.txtMovieTitle.text = result.data?.title
                    binding.txtReleaseDate.text = "Release Date: " + result.data?.releaseDate
                    binding.txtRating.text = """${result.data?.rating.toString()}/10"""
                    binding.txtMovieOverview.text = result.data?.overview

                }
                DataState.ERROR -> {
                    hideDialog()
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }
}