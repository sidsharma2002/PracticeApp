# PracticeApp

## Helping the Newbies

This app is made with a purpose to help newbies understand core concepts of Android Development.
There are very limited and not so clear resources available out there, Hence I've decided to fix it.

## Topics Implemented

#### Android Based
* [Background service](https://developer.android.com/guide/components/services)
* [Foreground service](https://developer.android.com/guide/components/services)
* [Broadcast Receiver](https://developer.android.com/reference/kotlin/android/content/BroadcastReceiver)
* [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager/basics)
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Fragment Transactions](https://developer.android.com/reference/androidx/fragment/app/FragmentTransaction)

#### Non-Android Based
* [Lambda functions](https://kotlinlang.org/docs/lambdas.html)
* [Higher Order functions](https://kotlinlang.org/docs/lambdas.html)
* [Returning functions](https://kotlinlang.org/docs/functions.html)
* Higher Order functions which returns a function (almost the same thing)

## Workflow

As soon the onCreateMethod is triggered, following things are done : 

* MyService (background service) is fired which gets killed after 2secs.
* MyForegroundService is created (if not already running) and keeps running in never ending loop.
* This foreground service then listens for network_changed broadcasts and shows a Toast.
* handleOnClickListener() implements listener for login button. This listener fires up a broadcast to the sameMyBroadcastReceiver. Also this listener replaces the current fragment with FragB and adds it to backstack.

When ever the app is launched for the first time, A 'Unique periodic work' is enqueued in the MyWorker.This periodic work shows notification every 15 minutes stating that workManager has been FiredUp

## Resources

#### Documentaion and Other Sources

- [Android Official Website](https://developer.android.com/docs) - Android Developer.
- [Kotlin Official Website](https://kotlinlang.org/) - A modern programming language that makes developers happier.
- [JetBrains Academy](https://www.jetbrains.com/academy/) - Learn to Program by Creating Working Applications.
- [Android Arsenal](https://android-arsenal.com/) -  Android developer portal with tools, libraries, and apps.
- [Android API Levels](https://apilevels.com/) - A quick reference table of Android versions with SDK & API levels, version codes, codenames, cumulative usage, and more.

#### Youtube Channels

* [CodingWithMitch](https://www.youtube.com/c/CodingWithMitch/featured "Named link title") : Learning to code by making real Android projects.
* [Phillip Lackner](https://www.youtube.com/c/PhilippLackner "Named link title") : Since the development niche is evolving so fast, I will help you to keep track of it so you don't feel lost in the jungle of coding.
* [Android Developers](https://www.youtube.com/user/androiddevelopers) : Official Channel for Android

#### Books

- [Android Apprentice](https://store.raywenderlich.com/products/android-apprentice) - Android Apprentice is the book for complete beginners to Android development.
- [Programming Android with Kotlin](https://learning.oreilly.com/library/view/programming-android-with/9781492062998/) - This book helps Android developers make the transition from Java to Kotlin and shows them how Kotlin provides a true advantage for gaining control over asynchronous computations.
- [Data Structures & Algorithms in Kotlin](https://store.raywenderlich.com/products/data-structures-and-algorithms-in-kotlin) - A book that teaches you the fundamental tools of implementing key data structures in Kotlin, and how to use them to solve algorithms.
- [Elements of Android Jetpack](https://commonsware.com/Jetpack/) - This book follows in the footsteps of The Busy Coder's Guide to Android Development, to introduce developers to Android app development, focusing on Jetpack. Here you will learn how to set up an Android app for Java or Kotlin, create a user interface, and more!
- [Advanced Android App Architecture](https://store.raywenderlich.com/products/advanced-android-app-architecture) - In Advanced Android App Architectures, you'll find a diverse and hands-on approach to architecting your apps on Android. Learn how to build scaleable and maintainable architectures in Android and Kotlin, including MVC, MVP, MVI, MVVM and VIPER!

#### Blogs

- [vogella](http://vogella.com/) - Android Tutorials by vogella.
- [CodingWithMitch](http://codingwithmitch.com/blog) - Blogs by CodingWithMitch community.
- [simplifiedcoding](https://www.simplifiedcoding.net/) - Learn building apps.
- [Kotlin hands-on](https://play.kotlinlang.org/hands-on/overview) - Tutorials by Kotlin Team
- [raywenderlich](https://www.raywenderlich.com/6649-kotlin-cheat-sheet-and-quick-reference) - Kotlin Cheat Sheet and Quick Reference.
