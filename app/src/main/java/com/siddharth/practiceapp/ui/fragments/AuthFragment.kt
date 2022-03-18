package com.siddharth.practiceapp.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentAuthBinding
import com.siddharth.practiceapp.manager.CurrentUserManager
import com.siddharth.practiceapp.manager.Router
import com.siddharth.practiceapp.ui.activites.MainActivity
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.snackBar
import com.siddharth.practiceapp.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenFromServer: String
    private val viewmodel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        subscribeToObservers()
        setupClickListeners()
        return binding.root
    }

    private fun subscribeToObservers() {
        viewmodel.account.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {
                    binding.progressAuth.isVisible = true
                    binding.btnGoogleLogin.isVisible = false
                }
                is Response.Success -> {
                    binding.progressAuth.isVisible = false
                    // saveDetails()
                    navigateToOnBoardingScreen()
                }
                is Response.Error -> {
                    it.message?.let { it1 -> snackBar(it1) }
                    binding.progressAuth.isVisible = false
                    binding.btnGoogleLogin.isVisible = true
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnGoogleLogin.setOnClickListener {
            binding.btnGoogleLogin.isVisible = false
            signIn()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewmodel.proceedToAuthenticate(task)
        }
    }

    private fun saveDetails() {
        val sharedPreferences = requireContext().getSharedPreferences(
            "userInfo",
            AppCompatActivity.MODE_PRIVATE
        )
        sharedPreferences.edit().apply {
            this.putBoolean("currentUser_isLoggedIn", true)
            this.putString("currentUser_uid", tokenFromServer)
            apply()
        }
        CurrentUserManager.isLoggedIn = true
        // NOTE this token string is very large and will occupy much space
        CurrentUserManager.currentUser.uid = tokenFromServer
    }

    private fun navigateToOnBoardingScreen() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<OnBoardingFragment>(R.id.auth_fragContainer)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1
    }
}