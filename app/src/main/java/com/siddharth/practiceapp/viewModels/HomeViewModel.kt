package com.siddharth.practiceapp.viewModels

import androidx.lifecycle.*
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.data.entities.HomeFeed
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

    private var _homeFeedList = MutableLiveData<Response<MutableList<HomeFeed>>>()
    val homeFeedList: LiveData<Response<MutableList<HomeFeed>>> = _homeFeedList

    private val _isMiscDialogAdded = MutableLiveData(false)
    val isMiscDialogAdded: LiveData<Boolean> = _isMiscDialogAdded

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.getAllHomeFeedList()
            _homeFeedList.postValue(Response.Loading(result.data))
            repository.getAndInsertHomeFeed() // insert in db
            result = repository.getAllHomeFeedList()
            _homeFeedList.postValue(result)
        }
    }

    fun miscDialogAdded(boolean: Boolean) {
        _isMiscDialogAdded.postValue(boolean)
    }
}