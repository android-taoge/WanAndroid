package com.example.jetpackpaging.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.jetpackpaging.adapter.LoadStateAdapter
import com.example.jetpackpaging.adapter.ProjectListAdapter
import com.example.jetpackpaging.databinding.FragmentProjectListBinding
import com.example.jetpackpaging.model.ProjectCate
import com.example.jetpackpaging.model.SkipWeb
import com.example.jetpackpaging.ui.ProjectListActivity.Companion.PROJECT_CATE
import com.example.jetpackpaging.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [ProjectListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@ExperimentalPagingApi
@AndroidEntryPoint
class ProjectListFragment : Fragment() {
    private lateinit var binding: FragmentProjectListBinding
    private val viewModel: ProjectViewModel by viewModels()

    //private val args: ProjectListFragmentArgs by navArgs()
    private var projectCate: ProjectCate? = null
    private val adapter = ProjectListAdapter()


    companion object {

        @JvmStatic
        fun newInstance(projectCate: ProjectCate) =
            ProjectListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PROJECT_CATE, projectCate)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectCate = arguments?.getSerializable(PROJECT_CATE) as ProjectCate
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


        projectCate?.id?.let { viewModel.fetchProjectList(it) }
        viewModel.projectList.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        })

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
        binding.rvProjectList.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.refresh.setOnRefreshListener {
            fetchProjectList()
        }


        adapter.onItemClick = { position, projectEntity ->
            projectEntity?.id?.let {
                SkipWeb(
                    id = projectEntity.id,
                    title = projectEntity.title,
                    link = projectEntity.link
                )
            }?.let { WebViewActivity.start2Web(requireActivity(), it) }

        }
    }


}