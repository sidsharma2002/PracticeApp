package com.siddharth.practiceapp.di

import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.repository.IHomeFeedRepository
import com.siddharth.practiceapp.repository.HomeFeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DefaultModule {

    // Lives as long as  ViewModel lives
    @ViewModelScoped
    @Provides
    fun providesRepository(newsApi: NewsApi, dao: HomeDataDao) =
        HomeFeedRepositoryImpl(newsApi, dao) as IHomeFeedRepository
}