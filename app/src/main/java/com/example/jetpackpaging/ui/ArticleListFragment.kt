package com.example.jetpackpaging.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.*
import com.example.jetpackpaging.adapter.ArticleListAdapter
import com.example.jetpackpaging.adapter.LoadStateAdapter
import com.example.jetpackpaging.adapter.NoMoreViewAdapter
import com.example.jetpackpaging.databinding.ArticleListFragmentBinding
import com.example.jetpackpaging.viewmodel.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
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

    private lateinit var concatAdapter : ConcatAdapter

    private val noMoreViewAdapter = NoMoreViewAdapter()
    private val contentAdapter = ArticleListAdapter { onItemClick(0) }

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
        binding.btnRetry.setOnClickListener { contentAdapter.retry() }
        initAdapter()
        fetchData()
    }

    private fun initAdapter() {

        concatAdapter =
            contentAdapter.withLoadStateHeaderAndFooter(
                LoadStateAdapter { contentAdapter.retry() },
                LoadStateAdapter { contentAdapter.retry() })
        binding.rvArticleList.adapter = concatAdapter


        // listen load state
        contentAdapter.addLoadStateListener { loadState ->
            //控制swipeRefreshLayout 显示与否
            binding.refreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
            binding.rvArticleList.isVisible = loadState.refresh is LoadState.NotLoading
            binding.progressbar.isVisible = loadState.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error

            //到达底部，没有更多数据显示no more data
            if (loadState.append.endOfPaginationReached) {
                if (!concatAdapter.adapters.contains(noMoreViewAdapter)) {
                    concatAdapter.addAdapter(noMoreViewAdapter)
                }
            } else {//隐藏no more data
                if (concatAdapter.adapters.contains(noMoreViewAdapter)) {
                    concatAdapter.removeAdapter(noMoreViewAdapter)
                }
            }

        }



        binding.refreshLayout.setOnRefreshListener {

            //adapter.refresh()  doesn't work
            fetchData()
        }


        binding.rvArticleList.addItemDecoration(
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
                    contentAdapter.submitData(it)
                }
            }
    }


    private fun onItemClick(id: Int = 0) {
        Toast.makeText(requireActivity(), "点击了", Toast.LENGTH_SHORT).show()
    }
}