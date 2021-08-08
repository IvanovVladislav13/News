package com.ivanov.newsapi.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    lateinit var navController: NavController
    private var binding: ActivityMainBinding? = null
    private val mBinding get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsAPI)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mToolbar = mBinding.toolbar
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(mToolbar)
        title = getString(R.string.news)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}