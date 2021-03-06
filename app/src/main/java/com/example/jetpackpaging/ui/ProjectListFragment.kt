package com.example.jetpackpaging.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.jetpackpaging.R
import com.example.jetpackpaging.adapter.LoadStateAdapter
import com.example.jetpackpaging.adapter.ProjectListAdapter
import com.example.jetpackpaging.databinding.FragmentProjectListBinding
import com.example.jetpackpaging.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_list_fragment.*
import kotlinx.android.synthetic.main.load_state_layout.view.*
import kotlinx.android.synthetic.main.page_state_layout.*
import kotlinx.coroutines.flow.collectLatest


/**
 * A simple [Fragment] subclass.
 * Use the [ProjectListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@ExperimentalPagingApi
@AndroidEntryPoint
class ProjectListFragment : Fragment() {

    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var binding: FragmentProjectListBinding
    private val adapter = ProjectListAdapter()
    private var categoryId: Int = 314

    companion object {
        @JvmStatic
        fun newInstance() =
            ProjectListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //categoryId = arguments?.get(resources.getString(R.string.project_cate_id)) as Int
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectListBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        fetchProjectList()
    }

    private fun fetchProjectList() {

        lifecycleScope.launchWhenCreated {
            viewModel.fetchProjectList(categoryId).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        binding.rvProjectList.adapter = adapter.withLoadStateHeaderAndFooter(
            LoadStateAdapter { adapter.retry() },
            LoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { state ->
            binding.apply {
                refresh.isRefreshing = state.refresh is LoadState.Loading
                rvProjectList.isVisible = state.refresh is LoadState.NotLoading
                pageState.progressbar.isVisible = state.refresh is LoadState.Loading
                pageState.btnRetry.isVisible = state.refresh is LoadState.Error

            }
        }

        binding.refresh.setOnRefreshListener {
            fetchProjectList()
        }
    }


}