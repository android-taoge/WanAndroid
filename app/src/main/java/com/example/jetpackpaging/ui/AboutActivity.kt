package com.example.jetpackpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.jetpackpaging.R

class AboutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val aboutHost = supportFragmentManager.findFragmentById(R.id.about_host) as NavHostFragment
        aboutHost.findNavController()
    }
}