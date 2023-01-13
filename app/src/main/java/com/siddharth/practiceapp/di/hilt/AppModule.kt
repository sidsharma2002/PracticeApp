package com.siddharth.practiceapp.di.hilt

import android.app.Application
import androidx.room.Room
import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.bitmapModifiers.BitmapModifier
import com.siddharth.practiceapp.data.database.HomeDataDatabase
import com.siddharth.practiceapp.notifications.NewsNotificationUseCase
import com.siddharth.practiceapp.repository.HomeDataMapper
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Constants.NEWS_BASE_URL
import com.siddharth.practiceapp.util.ErrorHandler
import com.siddharth.practiceapp.worker.NewsWorkerController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getBitmapModifier() : BitmapModifier = BitmapModifier()

    @Provides
    @Singleton
    fun getHomeDataMapper(): HomeDataMapper = HomeDataMapper()

    @Provides
    @Singleton
    fun getErrorHandler(): ErrorHandler = ErrorHandler()

    @Provides
    @Singleton
    fun getNewsWorkerController(
        newsNotificationUseCase: NewsNotificationUseCase,
        repository: HomeFeedRepository,
        homeDataMapper: HomeDataMapper
    ): NewsWorkerController =
        NewsWorkerController(newsNotificationUseCase, repository, homeDataMapper)

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApi =
        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideHomeDataDatabase(
        app: Application
    ) = Room.databaseBuilder(app, HomeDataDatabase::class.java, "homeData_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideHomeDataDao(db: HomeDataDatabase) = db.getHomeDataDao()
}