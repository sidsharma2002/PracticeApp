object Config {
    object Android {
        // Android sdk and version
        const val androidMinSdkVersion = 24
        const val androidTargetSdkVersion = 31
        const val androidCompileSdkVersion = 31
        const val androidBuildToolsVersion = "30.0.3"
    }

    object ClassPaths {
        const val androidGradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
        const val kotlinGradle =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val daggerHiltGradle =
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidVersion}"
        const val navigationSafArgsGradle =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.gradleNavigationArgVersion}"
        const val jitPackUrl = "https://jitpack.io"
        const val googleUrl = "https://maven.google.com/"
        const val pluginGradle = "https://plugins.gradle.org/m2/"
    }
}