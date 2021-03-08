package com.example.jetpackpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.commit
import androidx.paging.ExperimentalPagingApi
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ActivityMainBinding
import com.example.jetpackpaging.nav.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private var fragmentTag: String = "article"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mainBinding.root)
        initNavigation()
        showHideFragment(fragmentTag)
    }

    private fun initNavigation() {

        //使用扩展BottomNavigationView + navigation
       /* val navGraphIds = listOf(
            R.navigation.nav_article,
            R.navigation.nav_category,
            //R.navigation.nav_project,
            R.navigation.nav_mine
        )

        mainBinding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host,
            intent = intent
        )*/





        // 常规 frameLayout fragment show/hide
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.article -> {
                    fragmentTag = getString(R.string.article)
                    showHideFragment(fragmentTag)
                    true
                }

                R.id.category -> {
                    fragmentTag = getString(R.string.category)
                    showHideFragment(fragmentTag)
                    true
                }

                R.id.mine -> {
                    fragmentTag = getString(R.string.mine)
                    showHideFragment(fragmentTag)
                    true
                }

                else -> {
                    fragmentTag = getString(R.string.article)
                    showHideFragment(fragmentTag)
                    true
                }


            }
        }


    }


    private fun showHideFragment(fragmentTag: String) {
        val existFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        val fragments = supportFragmentManager.fragments
        supportFragmentManager.commit {

            if (fragments.isNotEmpty()) {
                for (fragment in fragments) {
                    hide(fragment)
                }
            }

            if (existFragment != null && fragments.contains(existFragment)) {
                show(existFragment)
                return@commit
            }

            when (fragmentTag) {
                getString(R.string.article) -> add(
                    R.id.main_container,
                    ArticleListFragment.newInstance(),
                    fragmentTag
                )
                getString(R.string.category) -> add(
                    R.id.main_container,
                    CategoryFragment.newInstance(),
                    fragmentTag
                )
                getString(R.string.mine) -> add(
                    R.id.main_container,
                    MineFragment.newInstance(),
                    fragmentTag
                )

            }

        }
    }

}