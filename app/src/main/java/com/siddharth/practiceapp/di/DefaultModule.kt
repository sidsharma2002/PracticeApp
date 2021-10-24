package com.siddharth.practiceapp.di

import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DefaultModule {

    // Lives as long as  ViewModel lives
    @ViewModelScoped
    @Provides
    fun providesRepository(newsApi: NewsApi, dao: HomeDataDao) =
        HomeFeedRepository(newsApi, dao) as DefaultHomeFeedRepository
}