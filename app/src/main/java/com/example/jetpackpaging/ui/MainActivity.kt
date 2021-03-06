package com.example.jetpackpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.ExperimentalPagingApi
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ActivityMainBinding
import com.example.jetpackpaging.nav.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mainBinding.root)
        initNavigation()
    }

    private fun initNavigation() {
        /* val navHostFragment =
             supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
         val navController = navHostFragment.findNavController()
         mainBinding.bottomNavigation.setupWithNavController(navController)*/


        val navGraphIds = listOf(
            R.navigation.nav_article,
            R.navigation.nav_category,
            R.navigation.nav_project,
            R.navigation.nav_mine
        )

        mainBinding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host,
            intent = intent
        )
    }
}