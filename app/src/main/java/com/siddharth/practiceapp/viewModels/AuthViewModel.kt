package com.siddharth.practiceapp.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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

    private val _tokenFromServer = MutableLiveData<Response<String?>>()
    val tokenFromServer : LiveData<Response<String?>> = _tokenFromServer

    fun proceedToAuthenticate(completedTask: Task<GoogleSignInAccount>) {
        _tokenFromServer.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val account =
                completedTask.getResult(ApiException::class.java)!!
            val scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE
            val token = GoogleAuthUtil.getToken(getApplication(), account.account, scope)
            val result = repository.authenticateUser(token)
            _tokenFromServer.postValue(result)
        }
    }
}