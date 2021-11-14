package com.siddharth.practiceapp.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultHomeFeedRepository
) : ViewModel() {

    private var _homeDataList = MutableLiveData<Response<MutableList<HomeData>>>()
    val homeDataList: LiveData<Response<MutableList<HomeData>>> = _homeDataList
    private val _isMiscDialogAdded = MutableLiveData(false)
    val isMiscDialogAdded : LiveData<Boolean> = _isMiscDialogAdded

    init {
        getNews()
    }

    private fun getNews() {
        //     experimentalHomeDataList.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.getAllHomeDataList()
            _homeDataList.postValue(Response.Loading(result.data))
            repository.getAndInsertTopNews() // insert in db
            result = repository.getAllHomeDataList()
            _homeDataList.postValue(result)
        }
    }

    fun miscDialogAdded(boolean: Boolean){
        _isMiscDialogAdded.postValue(boolean)
    }
}