package com.siddharth.practiceapp.ui.activites


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationBarView
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.ActivitySplashBinding
import com.siddharth.practiceapp.manager.CurrentUserManager
import com.siddharth.practiceapp.manager.Router
import com.siddharth.practiceapp.service.MyService
import com.siddharth.practiceapp.util.slideUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewsAndSharedPrefs(layoutInflater)
        setupUi()
        syncUserDetails()
        navigateToActivity()
    }

    private fun syncUserDetails() {
        setUserManagerDetailsFromSharedPrefs()
        fetchUserFromServerAndSetToUserManager()
    }

    private fun initViewsAndSharedPrefs(layoutInflater: LayoutInflater) {
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = this.getSharedPreferences("userInfo", MODE_PRIVATE)
    }

    private fun setupUi() {
        window.navigationBarColor = Color.BLACK
    }

    private fun setUserManagerDetailsFromSharedPrefs() {
        CurrentUserManager.isLoggedIn =
            sharedPref.getBoolean("currentUser_isLoggedIn", false)
        CurrentUserManager.currentUser.name =
            sharedPref.getString("currentUser_name", "unnamedUser").toString()
        CurrentUserManager.currentUser.uid =
            sharedPref.getString("currentUser_uid", "").toString()
    }

    /**
     * navigate to login screen  or mainActivity
     * NOTE : the delay must not be greater than 1.5 secs otherwise
     * it will annoy the user. Here the delay is just for reference purposes
     */
    private fun navigateToActivity() = lifecycleScope.launchWhenResumed {
        delay(800)
        runOnUiThread {
            navigateToMainActivity()
            finish()
        }
    }

    private fun navigateToMainActivity() {
        Router.with(this@SplashActivity)
            .getIntentForActivity(MainActivity::class.java)
            .also {
                startActivity(it)
            }
    }

    private fun fetchUserFromServerAndSetToUserManager() {
        // TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}