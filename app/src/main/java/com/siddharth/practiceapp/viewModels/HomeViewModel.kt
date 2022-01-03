package com.siddharth.practiceapp.viewModels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultHomeFeedRepository,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    private val dispatchersMain: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    private var _homeFeedList = MutableLiveData<Response<MutableList<HomeFeed>>>()
    val homeFeedList: LiveData<Response<MutableList<HomeFeed>>> = _homeFeedList

    // TODO refactor this
    val currentPage = MutableLiveData(1)
    val isNextPageLoading = MutableLiveData(false)

    init {
        getNews(true)
    }

    fun getNews(forFirstPage: Boolean) =
        viewModelScope.launch(dispatchersMain) {
            if (forFirstPage) {
                val result = getLocalAndSendAsLoadingForFirstPage()
                _homeFeedList.postValue(result)
            } else if (!forFirstPage) {
                isNextPageLoading.postValue(true)
                _homeFeedList.postValue(Response.LoadingForNextPage())
            }
            fetchFromNetworkAndSaveInDB(forFirstPage)
        }

    private suspend fun getLocalAndSendAsLoadingForFirstPage(): Response<MutableList<HomeFeed>> =
        withContext(dispatcherIO) {
            val result = repository.getAllHomeFeedList()
            Response.Loading(result.data)
        }

    private suspend fun fetchFromNetworkAndSaveInDB(forFirstPage: Boolean)  = withContext(dispatcherIO){
        repository.getAndInsertHomeFeed(forFirstPage) // insert in db
        val result = repository.getAllHomeFeedList()
        isNextPageLoading.postValue(false)
        _homeFeedList.postValue(result)
    }
}