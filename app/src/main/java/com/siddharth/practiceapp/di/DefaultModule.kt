package com.siddharth.practiceapp.di

import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.repository.DefaultRepository
import com.siddharth.practiceapp.repository.Repository
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
    fun providesRepository(newsApi: NewsApi, dao: HomeDataDao) = Repository(newsApi, dao) as DefaultRepository
}