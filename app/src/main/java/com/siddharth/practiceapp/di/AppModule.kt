package com.siddharth.practiceapp.di


import android.app.Application
import androidx.room.Room
import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.converter.ArrayListConverter
import com.siddharth.practiceapp.data.converter.ArticleConverter
import com.siddharth.practiceapp.data.database.HomeDataDatabase
import com.siddharth.practiceapp.util.Constants
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
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideNewsApi(BASE_URL: String): NewsApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)


    @Singleton
    @Provides
    fun provideHomeDataDatabase(
        app: Application
    ) =
        Room.databaseBuilder(app, HomeDataDatabase::class.java, "homeData_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideHomeDataDao(db: HomeDataDatabase) = db.getHomeDataDao()
}