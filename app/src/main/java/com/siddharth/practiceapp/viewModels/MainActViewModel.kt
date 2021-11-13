package com.siddharth.practiceapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActViewModel : ViewModel() {
    private val _appBarLoading = MutableLiveData(false)
    val appBarLoading: LiveData<Boolean> = _appBarLoading

    private val _showAppBarGreet = MutableLiveData(true)
    val showAppBarGreet: LiveData<Boolean> = _showAppBarGreet


    fun setAppBarLoading(bool: Boolean) {
        _appBarLoading.postValue(bool)
    }

    fun setShowAppBarGreet(bool: Boolean) {
        _showAppBarGreet.postValue(bool)
    }
}