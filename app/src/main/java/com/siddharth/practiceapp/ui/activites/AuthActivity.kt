package com.siddharth.practiceapp.ui.activites

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.ui.fragments.AuthFragment
import com.siddharth.practiceapp.ui.fragments.OnBoardingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setupUi()
    }

    private fun setupUi() {
        window.navigationBarColor = Color.BLACK
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<AuthFragment>(R.id.auth_fragContainer)
        }
    }
}