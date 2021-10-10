package com.siddharth.practiceapp.ui.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.siddharth.practiceapp.MainActivity
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.controllers.CurrentUserController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        syncDetails()
        navigateToActivity()
    }

    private fun navigateToActivity() {
        lifecycleScope.launchWhenResumed {
            delay(5000)
            runOnUiThread {
                Intent(this@SplashActivity, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun syncDetails() {

    }
}