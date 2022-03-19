package com.mkt.mobilecodetest.ui.main

import android.app.Activity
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkt.mobilecodetest.R
import com.mkt.mobilecodetest.common.AppConstants.BASE_IMG_URL
import com.mkt.mobilecodetest.common.BaseAdapter
import com.mkt.mobilecodetest.data.MovieListInfoModel
import com.mkt.mobilecodetest.ui.movie_detail.MovieDetailActivity


class MovieListAdapter () : BaseAdapter() {
    override fun onCreateCustomViewHolder(
        parent: ViewGroup?,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent!!.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )    }

    override fun onBindCustomViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(itemsList[position] as MovieListInfoModel, position)
    }

    override fun onCreateCustomHeaderViewHolder(
        parent: ViewGroup?,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return dummyHeader(parent!!.context)
    }

    override fun onBindCustomHeaderViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    inner class ViewHolder (itemView:View): RecyclerView.ViewHolder(itemView){
        fun bind(model: MovieListInfoModel, position: Int){
            resizeItem()

           var moviename = itemView.findViewById(R.id.tv_movie_name) as TextView
            moviename.text = model.title

            if ( model.posterPath == "") {
                Glide.with(itemView.context)
                    .load(R.drawable.img_placeholder)
                    .into(itemView.findViewById(R.id.ivImage))
            } else {
                Glide.with(itemView.context)
                    .load(BASE_IMG_URL + model.posterPath)
                    .into(itemView.findViewById(R.id.ivImage))
            }

            itemView.setOnClickListener {
                itemView.context.startActivity(MovieDetailActivity.getMovieDetailIntent(itemView.context,model.Id)
                )
            }
        }

        private fun resizeItem() {
            val display = (itemView.context as Activity).windowManager.defaultDisplay
            val size = Point()
            try {
                display.getRealSize(size)
            } catch (err: NoSuchMethodError) {
                display.getSize(size)
            }
            val width = size.x
            val height = size.y

            val lp = LinearLayout.LayoutParams((width * (1.0 / 3.0)).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 10, 0)

            itemView.layoutParams = lp
        }
    }
}