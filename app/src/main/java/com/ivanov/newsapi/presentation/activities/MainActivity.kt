package com.ivanov.newsapi.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var bottomNavView: BottomNavigationView
    lateinit var navController: NavController
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NewsAPI)
        super.onCreate(savedInstanceState)

        with(binding) {
            this@MainActivity.bottomNavView = bottomNavView
            this@MainActivity.toolbar = toolbar
        }
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.navigation_news_fragment,
                R.id.navigation_map_fragment,
                R.id.navigation_select_image_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)
        bottomNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}