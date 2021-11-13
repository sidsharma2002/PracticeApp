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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        printLifeCycleState("onCreate")

        // uncomment to start the services
        // manageService()

        // uncomment to start the worker
        // setupWorkManager()
        setupAppBarAnim(1)
        handleButtonClick()
    }

    private fun setupAppBarAnim(iterationNo: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            if (iterationNo > 2 || viewModel.showAppBarGreet.value!!.not()) return@launch
            if (iterationNo > 1) {
                binding.bottomAppBarTitle.fadeout(this@MainActivity, 1000)
                delay(2000)
            }
            binding.bottomAppBarTitle.text = "Hello there!!"
            binding.bottomAppBarTitle.slideUp(this@MainActivity, 1000, 200)
            delay(7000)
            binding.bottomAppBarTitle.fadeout(this@MainActivity, 1000)
            delay(2000)
            binding.bottomAppBarTitle.text = "What's up?"
            binding.bottomAppBarTitle.slideUp(this@MainActivity, 1000, 200)
            delay(7000)
            setupAppBarAnim(iterationNo.inc())   // recursion
        }
    }

    /**
     * enqueues a 'Unique Periodic Work'
     */
    private fun setupWorkManager() {
        val workManager = WorkManager.getInstance(this)
        val saveRequest =
            PeriodicWorkRequestBuilder<MyWorker>(10, TimeUnit.HOURS)
                .build()
        workManager.enqueueUniquePeriodicWork(
            "showNews",
            ExistingPeriodicWorkPolicy.KEEP, saveRequest
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageService() {
        startMyService()
        stopMyService(2000)

        startMyForegroundService()
        stopMyForegroundService(100000)
    }

    /**
     *  Intent can also be used to startService()
     */
    private fun startMyService() {
        Intent(this, MyService::class.java).also {
            startService(it)
        }
    }

    /**
     * stops the service after x seconds
     * NOTE : if the app gets killed before that,
     * then the service will run in a never ending loop
     */
    private fun stopMyService(x: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(x)
            Intent(applicationContext, MyService::class.java).also {
                stopService(it)
            }
        }
    }

    /**
     *  call startService when the activity is in the foreground
     *  if the app is in the background, call startForegroundService(it)
     *  Then we have time window of 5seconds to call startForeground
     *  in the MyForegroundService.class
     */
    private fun startMyForegroundService() {
        Intent(this, MyForegroundService::class.java).also {
            startService(it)
        }
    }

    /**
     *  this service will get automatically killed after1min by the system
     *  when the app (not activity) gets destroyed the service is also killed.
     *  startMyService() is called in onCreate method so whenever the
     *  activity is recreated the service also fires up.
     * NOTE : if we call other foreground service, then this service will also
     * behave like a foreground service
     */
    private fun stopMyForegroundService(x: Long) {
        lifecycleScope.launch {
            delay(x)
            Intent(applicationContext, MyForegroundService::class.java).also {
                stopService(it)
            }
        }
    }

    private fun handleButtonClick() {
        binding.bottomAppBar.setOnClickListener {
            val bottomFrag = MainBottomSheet()
            bottomFrag.show(supportFragmentManager, "bottom_nav_fragment")
        }
    }

    fun showSideBar() {
        binding.mainActivityProgressBar.isVisible = true
        //    binding.mainActivityProgressBar.slideUp(this, 800, 250)
    }

    fun hideSideBar() {
       // binding.mainActivityProgressBar.isVisible = false
            binding.mainActivityProgressBar.fadeout(this, 1500)
    }

    override fun onStart() {
        super.onStart()
        printLifeCycleState("onStart")
    }

    override fun onResume() {
        super.onResume()
        printLifeCycleState("onResume")
    }

    override fun onPause() {
        super.onPause()
        printLifeCycleState("onPause")
    }

    override fun onStop() {
        super.onStop()
        printLifeCycleState("onStop")
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