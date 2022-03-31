package com.siddharth.practiceapp.ui.fragments

import android.content.Intent
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
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentAuthBinding
import com.siddharth.practiceapp.manager.CurrentUserManager
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.snackBar
import com.siddharth.practiceapp.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


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
                    saveDetails()
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
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        val mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result?.isSuccess == true) {
                val acct = result.signInAccount
                if (acct != null) {
                    viewmodel.firebaseAuthWithGoogle(acct)
                }
            }
        }
    }


    private fun saveDetails() {
        val sharedPreferences = requireContext().getSharedPreferences(
            "userInfo",
            AppCompatActivity.MODE_PRIVATE
        )
        sharedPreferences.edit().apply {
            this.putBoolean("currentUser_isLoggedIn", true)
            this.putString("currentUser_uid", FirebaseAuth.getInstance().currentUser?.uid)
            apply()
        }
        CurrentUserManager.isLoggedIn = true
        CurrentUserManager.currentUser.uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        CurrentUserManager.currentUser.name =
            FirebaseAuth.getInstance().currentUser?.displayName.toString()
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