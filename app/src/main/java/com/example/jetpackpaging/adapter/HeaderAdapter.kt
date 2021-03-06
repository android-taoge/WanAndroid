package com.example.jetpackpaging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.BannerHeadLayoutBinding
import com.example.jetpackpaging.model.Banner
import com.example.jetpackpaging.model.SkipWeb
import com.example.jetpackpaging.ui.WebViewActivity
import com.youth.banner.indicator.CircleIndicator

class HeaderAdapter(private var banners: List<Banner>) :
    RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {


    fun setBanner(bannerList: List<Banner>) {
        banners = bannerList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

        holder.bindBannerAdapter(banners)
    }

    class HeaderViewHolder(private val headLayoutBinding: BannerHeadLayoutBinding) :
        RecyclerView.ViewHolder(headLayoutBinding.root) {

        fun bindBannerAdapter(bannerList: List<Banner>) {
            headLayoutBinding.banner.apply {
                adapter = BannerAdapter(bannerList)
                indicator = CircleIndicator(headLayoutBinding.root.context)
                adapter.setOnBannerListener { data, position ->

                    val banner = data as Banner
                    WebViewActivity.start2Web(
                        headLayoutBinding.root.context,
                        SkipWeb(banner.id, banner.url, banner.title)
                    )
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): HeaderViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.banner_head_layout, parent, false)
                return HeaderViewHolder(BannerHeadLayoutBinding.bind(view))
            }
        }
    }


}