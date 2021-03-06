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
    private lateinit var categoryAdapter: ListAdapter<ProjectCate, CategoryViewHolder>
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

        val diff = object : DiffUtil.ItemCallback<ProjectCate>() {
            override fun areItemsTheSame(oldItem: ProjectCate, newItem: ProjectCate): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(
                oldItem: ProjectCate,
                newItem: ProjectCate
            ): Boolean = oldItem == newItem
        }
        categoryAdapter = object : ListAdapter<ProjectCate, CategoryViewHolder>(diff) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
                CategoryViewHolder.create(parent)

            override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
                holder.bind(getItem(position), onItemClickListener)
        }

        binding.rvCate.adapter = categoryAdapter
        binding.rvCate.addItemDecoration(GridItemDecoration(2, 10))

    }


    class CategoryViewHolder(private val binding: ItemProjectCateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(projectCate: ProjectCate, itemClickListener: OnItemClickListener<ProjectCate>) {
            binding.tvTitle.text = projectCate.name
            binding.root.setOnClickListener {
                //TODO click
                itemClickListener.onItemClick(projectCate)
            }
        }


        companion object {
            fun create(parent: ViewGroup): CategoryViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_project_cate, parent, false)
                return CategoryViewHolder(ItemProjectCateBinding.bind(view))
            }
        }

    }


    val onItemClickListener = object : OnItemClickListener<ProjectCate> {
        override fun onItemClick(data: ProjectCate) {
            //Toast.makeText(requireActivity(), "hello${data.id}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_categoryFragment_to_projectListFragment,Bundle(data.id))
        }
    }


}