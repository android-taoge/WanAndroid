package com.example.jetpackpaging.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.*
import com.example.jetpackpaging.R
import com.example.jetpackpaging.`interface`.OnItemClickListener
import com.example.jetpackpaging.adapter.ProjectCateAdapter
import com.example.jetpackpaging.databinding.FragmentCategoryBinding
import com.example.jetpackpaging.databinding.ItemProjectCateBinding
import com.example.jetpackpaging.model.ProjectCate
import com.example.jetpackpaging.viewmodel.ProjectViewModel
import com.example.jetpackpaging.widget.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@ExperimentalPagingApi
@AndroidEntryPoint
class CategoryFragment : Fragment() {


    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: ProjectCateAdapter
    private val viewModel: ProjectViewModel by viewModels()

    companion object {

        @JvmStatic
        fun newInstance() =
            CategoryFragment()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCateAdapter()
        viewModel.projectCate.observe(viewLifecycleOwner, Observer {
            categoryAdapter.submitList(it)
        })
    }

    private fun initCateAdapter() {


        categoryAdapter = ProjectCateAdapter()
        binding.rvCate.adapter = categoryAdapter
        binding.rvCate.addItemDecoration(GridItemDecoration(2, 10))
        categoryAdapter.onItemClick = {
            //Toast.makeText(requireActivity(), "hello${it.id}", Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_categoryFragment_to_projectListFragment)
            ProjectListActivity.start2ProjectList(requireActivity(),it)
        }

    }


}