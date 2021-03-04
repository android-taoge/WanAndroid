package com.example.jetpackpaging.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.jetpackpaging.adapter.ArticleListAdapter
import com.example.jetpackpaging.adapter.LoadStateAdapter
import com.example.jetpackpaging.databinding.ArticleListFragmentBinding
import com.example.jetpackpaging.viewmodel.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_list_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class ArticleListFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    private lateinit var binding: ArticleListFragmentBinding

    private val viewModel: ArticleListViewModel by viewModels()

    private lateinit var adapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArticleListFragmentBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnRetry.setOnClickListener { adapter.retry() }
        initAdapter()
        fetchData()
    }

    private fun initAdapter() {
        adapter = ArticleListAdapter { onItemClick() }
        rv_articleList.adapter = adapter.withLoadStateFooter(
            LoadStateAdapter { adapter.retry() })

        adapter.addLoadStateListener { loadState ->
            binding.rvArticleList.isVisible = loadState.refresh is LoadState.NotLoading
            binding.progressbar.isVisible = loadState.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error
        }



        rv_articleList.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )


    }


    private var loadJob: Job? = null
    private fun fetchData() {
        loadJob?.cancel()
        loadJob =
            lifecycleScope.launch {
                viewModel.fetchArticle().collectLatest {

                    adapter.submitData(it)
                }
            }
    }


    private fun onItemClick(id: Int = 0) {
        Toast.makeText(requireActivity(), "点击了", Toast.LENGTH_SHORT).show()
    }
}