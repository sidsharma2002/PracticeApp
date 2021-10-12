package com.siddharth.practiceapp.ui.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.service.MyForegroundService
import com.siddharth.practiceapp.service.MyService
import com.siddharth.practiceapp.worker.MyWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

// @AndroidEntryPoint generates an individual Hilt component for each Android class in your project

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        printLifeCycleState("onCreate")

        // uncomment to start the services
        // manageService()

        // uncomment to start the worker
        setupWorkManager()
        handleButtonClick()
        bottomSheet()
    }

    /**
     * enqueues a 'Unique Periodic Work'
     */
    private fun setupWorkManager() {
            val workManager = WorkManager.getInstance(this)
            val saveRequest =
                PeriodicWorkRequestBuilder<MyWorker>(10, TimeUnit.HOURS)
                    .build()
            workManager.enqueueUniquePeriodicWork("showNews",
            ExistingPeriodicWorkPolicy.KEEP
                , saveRequest)
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
        Intent(this,MyService::class.java).also{
            startService(it)
        }
    }

    /**
     * stops the service after x seconds
     * NOTE : if the app gets killed before that,
     * then the service will run in a never ending loop
     */
    private fun stopMyService(x : Long) {
        lifecycleScope.launch(Dispatchers.IO){
            delay(x)
                Intent(applicationContext,MyService::class.java).also{
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
        Intent(this,MyForegroundService::class.java).also {
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
    private fun stopMyForegroundService(x : Long) {
        lifecycleScope.launch {
            delay(x)
            Intent(applicationContext,MyForegroundService::class.java).also {
                stopService(it)
            }
        }
    }

    //Bottom Sheet Fragment
    private fun bottomSheet(){

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(this@MainActivity, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(this@MainActivity, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(this@MainActivity, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(this@MainActivity, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(this@MainActivity, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@MainActivity, "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun handleButtonClick() {

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
        printLifeCycleState("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        printLifeCycleState("onRestart")
    }

    private fun printLifeCycleState(callbackName : String){
        println("Activity lifecycle state is : $callbackName +  " + lifecycle.currentState.name)
    }
}