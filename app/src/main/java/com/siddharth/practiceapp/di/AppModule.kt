package com.siddharth.practiceapp.di


import android.app.Application
import androidx.room.Room
import com.siddharth.practiceapp.api.HomeFeedApi
import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.database.HomeFeedDatabase
import com.siddharth.practiceapp.util.Constants.NEWS_BASE_URL
import com.siddharth.practiceapp.util.Constants.SERVER_BASE_URL
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
    fun providesNewsApi(): NewsApi =
        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun providesHomeFeedApi(): HomeFeedApi =
        Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeFeedApi::class.java)

    @Singleton
    @Provides
    fun provideHomeFeedDatabase(
        app: Application
    ) =
        Room.databaseBuilder(app, HomeFeedDatabase::class.java, "homeFeed_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideHomeFeedDao(db: HomeFeedDatabase) = db.getHomeFeedDao()
}