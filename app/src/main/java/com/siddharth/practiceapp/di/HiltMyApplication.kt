package com.siddharth.practiceapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/*All apps that use Hilt must contain an Application class that is annotated with @HiltAndroidApp.

@HiltAndroidApp triggers Hilt's code generation, including a base class for your application
that serves as the application-level dependency container.*/

@HiltAndroidApp
class HiltMyApplication : Application() {

}