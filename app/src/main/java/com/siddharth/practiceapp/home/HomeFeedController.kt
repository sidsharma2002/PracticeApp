package com.siddharth.practiceapp.home

import androidx.annotation.VisibleForTesting
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class HomeFeedController @Inject constructor(
    private val homeFeedRepository: HomeFeedRepository
) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val homeFeedViewMvcListener = object: HomeFeedViewMvc.Listener {
        override fun onHomeItemClicked(homeData: HomeData, position: Int) {
            /* no-op */
        }
    }

    private lateinit var viewMvc: HomeFeedViewMvc
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun onStart() = coroutineScope.launch {
        viewMvc.registerListener(homeFeedViewMvcListener)
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

    private fun isResponseSuccessAndDataNonNull(response: Response<List<HomeData>>): Boolean {
        return response is Response.Success && response.data.isNullOrEmpty().not()
    }

    fun onPause() {
        viewMvc.unregisterListener(homeFeedViewMvcListener)
    }

    fun bindView(viewMvc: HomeFeedViewMvc) {
        this.viewMvc = viewMvc
    }
}
