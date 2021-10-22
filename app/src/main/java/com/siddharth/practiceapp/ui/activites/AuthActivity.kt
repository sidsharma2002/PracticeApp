package com.siddharth.practiceapp.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.ui.fragments.AuthFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setupUi()
    }

    private fun setupUi() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<AuthFragment>(R.id.auth_fragContainer)
        }
    }
}