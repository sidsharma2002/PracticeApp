package com.siddharth.practiceapp.ui.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.manager.CurrentUserManager
import com.siddharth.practiceapp.service.MyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        syncDetails()
        startMyService()
        stopMyService(1000)
        navigateToActivity()
    }


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
     * navigate to login screen  or mainActivity
     * NOTE : the delay must not be greater than 1.5 secs otherwise
     * it will annoy the user. Here the delay is just for reference purposes
     */
    private fun navigateToActivity() {
        lifecycleScope.launchWhenResumed {
            delay(100)
            runOnUiThread {
                Intent(this@SplashActivity, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun syncDetails() {
        setFromSharedPrefs()
        fetchFromServer()
    }

    private fun setFromSharedPrefs() {
        val sharedPref = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        CurrentUserManager.currentUser.name =
            sharedPref.getString("currentUser_name", "unknownUser").toString()
        CurrentUserManager.currentUser.uid =
            sharedPref.getString("currentUser_uid", "").toString()
    }

    private fun fetchFromServer() {
       // TODO("Not yet implemented")
    }
}