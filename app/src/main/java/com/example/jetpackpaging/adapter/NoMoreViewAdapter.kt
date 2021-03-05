package com.example.jetpackpaging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.LoadStateLayoutBinding

class NoMoreViewAdapter():RecyclerView.Adapter<NoMoreViewHolder>() {




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):NoMoreViewHolder {
        return NoMoreViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(
        holder: com.example.jetpackpaging.adapter.NoMoreViewHolder,
        position: Int
    ) {


    }
}

class NoMoreViewHolder(private val binding: LoadStateLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {






    companion object {
        fun create(parent: ViewGroup): NoMoreViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_layout, parent, false)
            val binding = LoadStateLayoutBinding.bind(view)
            return NoMoreViewHolder(binding)
        }
    }
}

