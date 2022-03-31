package com.siddharth.practiceapp.ui.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.ActivityMainBinding
import com.siddharth.practiceapp.databinding.FragmentFragABinding
import com.siddharth.practiceapp.service.MyForegroundService
import com.siddharth.practiceapp.service.MyService
import com.siddharth.practiceapp.ui.fragments.MainBottomSheet
import com.siddharth.practiceapp.util.fadeout
import com.siddharth.practiceapp.util.slideUp
import com.siddharth.practiceapp.viewModels.MainActViewModel
import com.siddharth.practiceapp.worker.MyWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

// @AndroidEntryPoint generates an individual Hilt component for each Android class in your project

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavBar()
    }

    private fun setupNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setOnItemReselectedListener { Unit }
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        printLifeCycleState("onDestroy")
    }

    private fun printLifeCycleState(callbackName: String) {
        println("Activity lifecycle state is : $callbackName +  " + lifecycle.currentState.name)
    }
}