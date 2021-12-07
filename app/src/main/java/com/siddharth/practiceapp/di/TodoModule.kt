package com.siddharth.practiceapp.di

import com.siddharth.practiceapp.api.HomeFeedApi
import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.data.dao.TodoDao
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object TodoModule {

    // Lives as long as  ViewModel lives
    @ViewModelScoped
    @Provides
    fun providesRepository( dao: TodoDao) =
        TodoRepository(dao)
}