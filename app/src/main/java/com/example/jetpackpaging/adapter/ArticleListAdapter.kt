package com.example.jetpackpaging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ItemArticleLayoutBinding
import com.example.jetpackpaging.model.ArticleEntity
import java.text.SimpleDateFormat

class ArticleListAdapter(private val itemClick: (articleId: Int) -> Unit) :
    PagingDataAdapter<ArticleEntity, ArticleListAdapter.ArticleViewHolder>(differ) {


    companion object {
        val differ = object : DiffUtil.ItemCallback<ArticleEntity>() {
            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent, itemClick)
    }


    class ArticleViewHolder(
        private val binding: ItemArticleLayoutBinding,
        private val click: (articleId: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(articleEntity: ArticleEntity?) {
            articleEntity?.let { article ->
                binding.apply {
                    tvTitle.text = article.title
                    tvDesc.text = article.desc
                    tvAuthor.text = article.author
                    tvTime.text = SimpleDateFormat("yyyy-MM-dd").format(article.publishTime)
                }

                binding.root.setOnClickListener {
                    click.invoke(article.id)
                }
            }
        }


        companion object {
            fun create(parent: ViewGroup, click: (articleId: Int) -> Unit): ArticleViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_article_layout, parent, false)
                val binding = ItemArticleLayoutBinding.bind(view)
                return ArticleViewHolder(binding, click)
            }
        }
    }


}