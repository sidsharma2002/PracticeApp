package com.siddharth.practiceapp.di

import com.siddharth.practiceapp.api.HomeFeedApi

import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.repository.HomeFeedRepository

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
    fun providesRepository(homeFeedApi: HomeFeedApi, dao: HomeFeedDao) =
        HomeFeedRepository(homeFeedApi, dao) as DefaultHomeFeedRepository
}