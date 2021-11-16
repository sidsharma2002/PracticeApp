package com.siddharth.practiceapp.di


import com.siddharth.practiceapp.repository.AuthRepository
import com.siddharth.practiceapp.repository.DefaultAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

//    @Provides
//    @ViewModelScoped
//    @Named("AuthApi")
//    fun providesAuthApi(AUTH_BASE_URL: String = Constants.AUTH_BASE_URL): AuthApi =
//        Retrofit.Builder()
//            .baseUrl(AUTH_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(AuthApi::class.java)

    // Lives as long as  ViewModel lives
    @ViewModelScoped
    @Provides
    fun providesAuthRepository() = AuthRepository() as DefaultAuthRepository
}