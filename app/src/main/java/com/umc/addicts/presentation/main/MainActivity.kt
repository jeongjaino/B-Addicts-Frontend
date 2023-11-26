package com.umc.addicts.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.umc.addicts.R
import com.umc.addicts.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavController()
    }

    private fun initNavController(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main_fragmentContainverView)
                    as NavHostFragment
        navController = navHostFragment.navController
        binding.bnvMainBottomNaviView.setupWithNavController(navController)
        setBottomNavigationVisiblity()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setBottomNavigationVisiblity() {
        val hideBottomNavigationFragments = mutableListOf<Int>()
        val typedArray = resources.obtainTypedArray(R.array.hide_bottomNavigation_fragments)
        for (index in 0..typedArray.length()) {
            hideBottomNavigationFragments.add(typedArray.getResourceId(index, 0))
        }

        typedArray.recycle()
        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.bnvMainBottomNaviView.isVisible =
                (destination.id !in hideBottomNavigationFragments)
        }
    }
}