package com.siddharth.practiceapp.home

import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFeedController @Inject constructor(
    private val homeFeedRepository: HomeFeedRepository
) {

    private lateinit var viewMvc: HomeFeedViewMvc
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun onStart() = coroutineScope.launch {
        fetchHomeFeedDataAndBindToView()
    }

    private suspend fun fetchHomeFeedDataAndBindToView() {
        fetchHomeDataListFromLocalDbAndBindItToView()
        homeFeedRepository.fetchNewsFromServerAndInsertInLocalDb()
        fetchHomeDataListFromLocalDbAndBindItToView()
    }

    private suspend fun fetchHomeDataListFromLocalDbAndBindItToView() {
        val response = homeFeedRepository.getAllHomeDataList()

        if (isResponseSuccessAndDataNonNull(response)) {
            viewMvc.bindHomeDataListToView(response.data!!)
        }
    }

    fun bindView(viewMvc: HomeFeedViewMvc) {
        this.viewMvc = viewMvc
    }

    private fun isResponseSuccessAndDataNonNull(response: Response<List<HomeData>>): Boolean {
        return response is Response.Success && response.data.isNullOrEmpty().not()
    }
}
