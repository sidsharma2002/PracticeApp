package com.siddharth.practiceapp.viewModels

import androidx.lifecycle.*
import com.siddharth.practiceapp.adapter.HomeRvAdapter
import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.repository.DefaultRepository
import com.siddharth.practiceapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultRepository
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
        experimentalHomeDataList.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            experimentalHomeDataList.postValue(repository.getAllHomeDataList())
            repository.getTopNews() // insert in db
            val result = repository.getAllHomeDataList()    // get from db
            experimentalHomeDataList.postValue(result)
        }
    }
}