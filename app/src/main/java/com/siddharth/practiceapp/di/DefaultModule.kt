package com.siddharth.practiceapp.di

import com.siddharth.practiceapp.repository.DefaultRepository
import com.siddharth.practiceapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn( ViewModelComponent :: class)
object DefaultModule {
    // Lives as long as  ViewModel lives
    @ViewModelScoped
    @Provides
    fun providesRepository() = Repository() as DefaultRepository
}