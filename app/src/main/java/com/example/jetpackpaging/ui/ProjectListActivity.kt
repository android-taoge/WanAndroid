package com.example.jetpackpaging.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.paging.ExperimentalPagingApi
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ActivityProjectListBinding
import com.example.jetpackpaging.model.ProjectCate
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class ProjectListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectListBinding

    companion object {
        const val PROJECT_CATE = "project_cate"

        fun start2ProjectList(context: Context, projectCate: ProjectCate) {
            val intent = Intent(context, ProjectListActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(PROJECT_CATE, projectCate)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val projectCate = intent.extras?.get(PROJECT_CATE) as ProjectCate

        initFragment(projectCate)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initFragment(projectCate: ProjectCate) {

        binding.titleBar.toolbar.apply {
            title = projectCate.name
            setNavigationOnClickListener {
                this@ProjectListActivity.finish()
            }
        }

        supportFragmentManager.commit {
            val projectListFragment = ProjectListFragment.newInstance(projectCate)
            add(R.id.container_projects, projectListFragment)
            show(projectListFragment)
        }
    }
}