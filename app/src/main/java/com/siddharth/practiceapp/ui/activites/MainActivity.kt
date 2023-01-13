package com.siddharth.practiceapp.ui.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.siddharth.practiceapp.databinding.ActivityMainBinding
import com.siddharth.practiceapp.service.MyForegroundService
import com.siddharth.practiceapp.service.MyService
import com.siddharth.practiceapp.util.fadeout
import com.siddharth.practiceapp.util.slideUp
import com.siddharth.practiceapp.viewModels.MainActViewModel
import com.siddharth.practiceapp.worker.NewsWorker
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
        printLifeCycleState("onCreate")
        initViewBinding()
        setupMyServices()
        setupWorkManager()
        handleButtonClick()

        lifecycleScope.launch {
            setupAppBarAnim(iterationNo = 1)
        }
    }

    private fun initViewBinding() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMyServices() {
        startMyService()
        stopMyService(2000)

        startMyForegroundService()
        stopMyForegroundService(100 * 1000)
    }

    private suspend fun setupAppBarAnim(iterationNo: Int) {
        if (iterationNo > 2 || shouldShowAppGreet().not()) {
            return
        }

        if (iterationNo > 1) {
            fadeoutBottomAppBarTitle()
            delay(2000)
        }

        setTextAndSlideUpBottomAppBarTitle("Hello There!!")
        delay(7000)
        fadeoutBottomAppBarTitle()
        delay(2000)
        setTextAndSlideUpBottomAppBarTitle("What's up!")
        delay(7000)
        setupAppBarAnim(iterationNo.inc())   // recur for continuous animation

    }

    private fun shouldShowAppGreet() : Boolean {
        return viewModel.showAppBarGreet.value!!
    }

    private fun fadeoutBottomAppBarTitle(animTime : Int = 1000) {
        binding.bottomAppBarTitle.fadeout(this@MainActivity, 1000)
    }

    private fun setTextAndSlideUpBottomAppBarTitle(text : String) {
        binding.bottomAppBarTitle.text = text
        binding.bottomAppBarTitle.slideUp(this@MainActivity, 1000, 200)
    }

    /**
     * enqueues a 'Unique Periodic Work'
     */
    private fun setupWorkManager() {
        val workManager = WorkManager.getInstance(this)
        val saveRequest =
            PeriodicWorkRequestBuilder<NewsWorker>(
                repeatInterval = NewsWorker.INTERVAL_HOURS,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            ).build()

        workManager.enqueueUniquePeriodicWork(
            /* uniqueWorkName = */ "showNews",
            /* existingPeriodicWorkPolicy = */ ExistingPeriodicWorkPolicy.REPLACE,
            /* periodicWork = */ saveRequest
        )
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