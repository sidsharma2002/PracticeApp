package com.siddharth.practiceapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.siddharth.practiceapp.broadcastReceiver.MyBroadcastReceiver
import com.siddharth.practiceapp.fragments.FragB
import com.siddharth.practiceapp.service.MyForegroundService
import com.siddharth.practiceapp.service.MyService
import com.siddharth.practiceapp.worker.MyWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var workManager : WorkManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        printLifeCycleState("onCreate")

        manageService()
        setupWorkManager()
        handleButtonClick()
    }

    private fun setupWorkManager() {
            workManager = WorkManager.getInstance(this)
            workManager.cancelAllWork()
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()
            val saveRequest =
                PeriodicWorkRequestBuilder<MyWorker>(30, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()
            workManager.enqueue(saveRequest)
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

    private fun handleButtonClick() {
        val button : Button = findViewById(R.id.loginButton)
        button.setOnClickListener {
            // Intent can also be used to send Broadcasts
            Intent(applicationContext,MyBroadcastReceiver::class.java).also {
                sendBroadcast(it)
            }
            // Fragment Transaction
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<FragB>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }
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