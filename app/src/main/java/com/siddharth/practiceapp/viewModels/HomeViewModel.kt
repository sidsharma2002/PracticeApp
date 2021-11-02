package com.siddharth.practiceapp.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultHomeFeedRepository
) : ViewModel() {

    private val _news = MutableLiveData<Response<News?>>()
    val news: LiveData<Response<News?>> = _news

    val homeDataList = news.switchMap {
        mapHomeDataFromNews(it)
    }

    val experimentalHomeDataList = MutableLiveData<Response<List<HomeData>>>()

    init {
        getNews()
    }

    private fun mapHomeDataFromNews(news: Response<News?>): LiveData<Response<ArrayList<HomeData>>> {
        val listHomeData = ArrayList<HomeData>()
        val articleList = news.data?.articles
//        articleList?.forEach {
//            listHomeData.add(HomeData(HomeRvAdapter.newsType, id = it,id = it.url))
//        }
        val finalMappedList = MutableLiveData<Response<ArrayList<HomeData>>>()
        finalMappedList.value = Response.Success(listHomeData)
        return finalMappedList
    }

    private fun getNews() {
        //     experimentalHomeDataList.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("vm","47")
            var result = repository.getAllHomeDataList()
            Log.d("vm","49 " + experimentalHomeDataList.value?.toString())
            experimentalHomeDataList.postValue(Response.Loading(result.data))
            Log.d("vm","51")
            repository.getAndInsertTopNews() // insert in db
            result = repository.getAllHomeDataList()
            experimentalHomeDataList.postValue(result)
            Log.d("homeVM", experimentalHomeDataList.value?.data?.size.toString())
        }
    }
}