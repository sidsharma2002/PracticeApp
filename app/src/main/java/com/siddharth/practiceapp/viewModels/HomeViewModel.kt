package com.siddharth.practiceapp.viewModels

import androidx.lifecycle.*
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.repository.IHomeFeedRepository
import com.siddharth.practiceapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IHomeFeedRepository
) : ViewModel() {

    // ui observes this, update this to notify data to ui.
    val homeDataListLiveData = MutableLiveData<Response<List<HomeData>>>()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            homeDataListLiveData.postValue(repository.getHomeDataList())
            repository.fetchAndInsertTopNewsInDb()
            homeDataListLiveData.postValue(repository.getHomeDataList())
          }
    }
}