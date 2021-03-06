package com.example.jetpackpaging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ItemProjectCateBinding
import com.example.jetpackpaging.model.ProjectCate

class ProjectCateAdapter :
    ListAdapter<ProjectCate, ProjectCateAdapter.CategoryViewHolder>(diff) {


    var onItemClick: ((ProjectCate) -> Unit?)? = null

    companion object {
        val diff = object : DiffUtil.ItemCallback<ProjectCate>() {
            override fun areItemsTheSame(oldItem: ProjectCate, newItem: ProjectCate): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(
                oldItem: ProjectCate,
                newItem: ProjectCate
            ): Boolean = oldItem == newItem
        }
    }

    inner class CategoryViewHolder(
        private val binding: ItemProjectCateBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                //TODO click
                onItemClick?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun bind(projectCate: ProjectCate) {
            binding.tvTitle.text = projectCate.name

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_cate, parent, false)
        return CategoryViewHolder(ItemProjectCateBinding.bind(view))

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}








