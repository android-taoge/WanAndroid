package com.example.jetpackpaging.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ItemProjectListBinding
import com.example.jetpackpaging.model.ProjectEntity
import java.text.SimpleDateFormat

class ProjectListAdapter :
    PagingDataAdapter<ProjectEntity, ProjectListAdapter.ProjectViewHolder>(diff) {


    var onItemClick: ((Int, ProjectEntity?) -> Unit?)? = null

    companion object {
        val diff = object : DiffUtil.ItemCallback<ProjectEntity>() {
            override fun areItemsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: ProjectEntity,
                newItem: ProjectEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class ProjectViewHolder(private val binding: ItemProjectListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(absoluteAdapterPosition, getItem(absoluteAdapterPosition))
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(projectEntity: ProjectEntity?) {
            binding.apply {
                projectEntity?.let {
                    tvTitle.text=it.title
                    tvDesc.text=it.desc
                    tvAuthor.text=it.author
                    tvPublishtime.text=SimpleDateFormat("yyyy-MM-dd").format(it.publishTime)

                    Glide.with(binding.root.context)
                        .load(it.envelopePic)
                        .into(imageView)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_project_list, parent, false)
        return ProjectViewHolder(ItemProjectListBinding.bind(view))
    }
}