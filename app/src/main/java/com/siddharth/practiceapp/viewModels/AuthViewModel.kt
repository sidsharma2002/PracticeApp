package com.siddharth.practiceapp.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.siddharth.practiceapp.repository.DefaultAuthRepository
import com.siddharth.practiceapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val repository: DefaultAuthRepository
) : AndroidViewModel(application) {

    private val _account = MutableLiveData<Response<GoogleSignInAccount>>()
    val account: LiveData<Response<GoogleSignInAccount>> = _account


    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        _account.postValue(Response.Loading())
        val auth = FirebaseAuth.getInstance();
        viewModelScope.launch(Dispatchers.IO) {
            val credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            auth.signInWithCredential(credential)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            _account.postValue(Response.Success(acct))
                        }
                    }
                })

        }
    }


    fun proceedToAuthenticate(completedTask: Task<GoogleSignInAccount>) {
        _account.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val account =
                    completedTask.getResult(ApiException::class.java)!!
                _account.postValue(Response.Success(account))
            } catch (e: ApiException) {
                _account.postValue(Response.Error(e.localizedMessage ?: "some error occurred"))
            }
        }
    }
}