package com.siddharth.practiceapp.di.hilt

import com.siddharth.practiceapp.notifications.NewsNotificationUseCase
import com.siddharth.practiceapp.notifications.NewsNotificationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    abstract fun bindNewsNotificationUseCase(
        newsNotificationUseCaseImpl: NewsNotificationUseCaseImpl
    ): NewsNotificationUseCase
}