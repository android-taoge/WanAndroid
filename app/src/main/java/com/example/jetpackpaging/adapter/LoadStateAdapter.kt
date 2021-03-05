package com.example.jetpackpaging.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.LoadStateLayoutBinding

class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadFootViewHolder>() {


    override fun onBindViewHolder(holder: LoadFootViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadFootViewHolder {
        return LoadFootViewHolder.create(parent, retry)
    }


}

class LoadFootViewHolder(private val binding: LoadStateLayoutBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    private val TAG = NoMoreViewHolder::class.java.simpleName

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }


    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvError.text = loadState.error.localizedMessage
        }
        binding.tvError.isVisible = loadState !is LoadState.Loading
        binding.progressbar.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState !is LoadState.Loading
        Log.e(TAG, "endPage==${loadState.endOfPaginationReached}")
        binding.tvNomore.isVisible = loadState.endOfPaginationReached
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadFootViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_layout, parent, false)
            val binding = LoadStateLayoutBinding.bind(view)
            return LoadFootViewHolder(binding, retry)
        }
    }
}
