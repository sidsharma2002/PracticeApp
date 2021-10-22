package com.siddharth.practiceapp.ui.activites


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationBarView
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.manager.CurrentUserManager
import com.siddharth.practiceapp.manager.Router
import com.siddharth.practiceapp.service.MyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.navigationBarColor = resources.getColor(R.color.purple_200)
        setContentView(R.layout.activity_splash)

        sharedPref = this.getSharedPreferences("userInfo", MODE_PRIVATE)
        syncDetails()
        startMyService()
        stopMyService(1000)
        navigateToActivity()
    }

    private fun syncDetails() {
        setFromSharedPrefs()
        fetchFromServer()
    }

    private fun setFromSharedPrefs() {
        CurrentUserManager.isLoggedIn =
            sharedPref.getBoolean("currentUser_isLoggedIn", false)
        CurrentUserManager.currentUser.name =
            sharedPref.getString("currentUser_name", "unknownUser").toString()
        CurrentUserManager.currentUser.uid =
            sharedPref.getString("currentUser_uid", "").toString()
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
            delay(1500)
            runOnUiThread {
                when (CurrentUserManager.isLoggedIn) {
                    false -> {
                        Router.with(this@SplashActivity)
                            .getIntentForActivity(MainActivity::class.java)
                            .also {
                                startActivity(it)
                            }
                    }
                    true -> {
                        Router.with(this@SplashActivity)
                            .getIntentForActivity(MainActivity::class.java)
                            .also {
                                startActivity(it)
                            }
                    }
                }
                finish()
            }

        }
    }

    private fun fetchFromServer() {
        // TODO("Not yet implemented")
    }
}