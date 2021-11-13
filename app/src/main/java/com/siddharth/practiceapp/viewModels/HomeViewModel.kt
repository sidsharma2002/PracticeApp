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

    val homeDataList = MutableLiveData<Response<List<HomeData>>>()

    init {
        getNews()
    }

    private fun getNews() {
        //     experimentalHomeDataList.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.getAllHomeDataList()
            homeDataList.postValue(Response.Loading(result.data))
            repository.getAndInsertTopNews() // insert in db
            result = repository.getAllHomeDataList()
            homeDataList.postValue(result)
        }
    }
}