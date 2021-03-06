package com.example.jetpackpaging.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpackpaging.model.Banner
import com.example.jetpackpaging.model.SkipWeb
import com.example.jetpackpaging.ui.WebViewActivity
import com.youth.banner.adapter.BannerAdapter

class BannerAdapter(data: List<Banner>) :
    BannerAdapter<Banner, com.example.jetpackpaging.adapter.BannerAdapter.HeadViewHolder>(data) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): HeadViewHolder {
        return HeadViewHolder.create(parent)
    }

    override fun onBindView(holder: HeadViewHolder?, data: Banner?, position: Int, size: Int) {

        data?.let { holder?.bind(it) }

    }


    class HeadViewHolder(private val imageView: ImageView) :
        RecyclerView.ViewHolder(imageView) {


        fun bind(banner: Banner) {
            Glide.with(imageView)
                .load(banner.imagePath)
                .into(imageView)

        }

        companion object {
            fun create(parent: ViewGroup?): HeadViewHolder {
                val imageView = ImageView(parent?.context)
                imageView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                return HeadViewHolder(imageView)
            }
        }
    }

}