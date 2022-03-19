package com.mkt.mobilecodetest.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkt.mobilecodetest.R
import com.mkt.mobilecodetest.common.BaseAdapter
import com.mkt.mobilecodetest.common.BaseViewBinding
import com.mkt.mobilecodetest.data.DataState
import com.mkt.mobilecodetest.databinding.ActivityMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : BaseViewBinding() {
    lateinit var viewModel: MovieListViewModel

    lateinit var binding: ActivityMovieListBinding

    lateinit var popularAdapter: MovieListAdapter

    lateinit var upcomingAdapter: MovieListAdapter

    override fun getView(): View {
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUpContents(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

        popularAdapter = MovieListAdapter()
        upcomingAdapter = MovieListAdapter()

        binding.rvPopularList.setHasFixedSize(true)
        binding.rvPopularList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularList.adapter = popularAdapter

        binding.rvUpcomingList.setHasFixedSize(true)
        binding.rvUpcomingList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingList.adapter = upcomingAdapter

        viewModel.page = 1
        observePopularMovieList()
        getPopularMovieList()
        observeUpcomingMovieList()
        getUpcomingMovieList()
    }

    private fun getPopularMovieList() {
        popularAdapter.clear()
        viewModel.observePopularMovieList()
    }

    private fun observePopularMovieList() {
        popularAdapter.clear()

        viewModel.getPopularMovieList.observe(this, Observer {
            when (it.status) {
                DataState.LOADING -> {
                    popularAdapter.showLoading()
                }
                DataState.SUCCESS -> {
                    popularAdapter.clearFooter()
                    for (movie in it.data!!.results) {
                        popularAdapter.add(movie)
                    }
                }
                DataState.ERROR -> {
                    popularAdapter.clearFooter()
                    Toast.makeText(this,"Error retriving data!",Toast.LENGTH_SHORT).show()
                    popularAdapter.showRetry(R.layout.item_retry,R.id.server_retry_container,
                        object : BaseAdapter.OnRetryListener{
                            override fun onRetry() {
                                getPopularMovieList()
                            }

                        })
                }
            }
        })
    }

    private fun getUpcomingMovieList() {
        upcomingAdapter.clear()
        viewModel.observeUpcomingMovieList()
    }

    private fun observeUpcomingMovieList() {
        upcomingAdapter.clear()

        viewModel.getUpcomingMovieList.observe(this, Observer {
            when (it.status) {
                DataState.LOADING -> {
                    upcomingAdapter.showLoading()
                }
                DataState.SUCCESS -> {
                    upcomingAdapter.clearFooter()
                    for (movie in it.data!!.results) {
                        upcomingAdapter.add(movie)
                    }
                }
                DataState.ERROR -> {
                    upcomingAdapter.clearFooter()
                    Toast.makeText(this,"Error retriving data!",Toast.LENGTH_SHORT).show()
                    upcomingAdapter.showRetry(R.layout.item_retry,R.id.server_retry_container,
                        object : BaseAdapter.OnRetryListener{
                            override fun onRetry() {
                                getUpcomingMovieList()
                            }

                        })
                }
            }
        })
    }

}