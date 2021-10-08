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
* [Bitmaps](https://developer.android.com/topic/performance/graphics)
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)

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

When ever the app is launched for the first time, A 'Unique periodic work' is enqueued in the MyWorker.This periodic work shows notification every 15 minutes stating that workManager has been FiredUp.

In the fragment A, click on the start work button and select an image, the app will apply a grey filter to it while recovering the bitmap on configuration changes.

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

- [MindOrks](https://blog.mindorks.com) - Blogs by amit shekhar sir (respect++)
- [vogella](http://vogella.com/) - Android Tutorials by vogella.
- [CodingWithMitch](http://codingwithmitch.com/blog) - Blogs by CodingWithMitch community.
- [simplifiedcoding](https://www.simplifiedcoding.net/) - Learn building apps.
- [Kotlin hands-on](https://play.kotlinlang.org/hands-on/overview) - Tutorials by Kotlin Team
- [raywenderlich](https://www.raywenderlich.com/6649-kotlin-cheat-sheet-and-quick-reference) - Kotlin Cheat Sheet and Quick Reference.

## Android Best Practices

#### Use Default Gradle Project Structure
- Your default option should be Gradle using the Android Gradle plugin.
- It is important that your application's build process is defined by your Gradle files, rather than being reliant on IDE specific configurations. This allows for consistent builds between tools and better support for continuous integration systems.

#### Store Passwords and Sensitive Date in gradle.properties

- In your app's `build.gradle` you will need to define the `signingConfigs` for the release build. Here is what you should avoid:

 - _Don't do this_. This would appear in the version control system.

```groovy
signingConfigs {
    release {
        // DON'T DO THIS!!
        storeFile file("myapp.keystore")
        storePassword "password123"
        keyAlias "thekey"
        keyPassword "password789"
    }
}
```

- Instead, make a `gradle.properties` file which should _not_ be added to the version control system:

```
KEYSTORE_PASSWORD=password123
KEY_PASSWORD=password789
```

- That file is automatically imported by Gradle, so you can use it in `build.gradle` as such:

```groovy
signingConfigs {
    release {
        try {
            storeFile file("myapp.keystore")
            storePassword KEYSTORE_PASSWORD
            keyAlias "thekey"
            keyPassword KEY_PASSWORD
        }
        catch (ex) {
            throw new InvalidUserDataException("You should define KEYSTORE_PASSWORD and KEY_PASSWORD in gradle.properties.")
        }
    }
}
```
#### Choose the App Architecture Wisely

- The architecture defines where the application performs its core functionality and how that functionality interacts with things like the database and the user interface.
- We have many architectures like MVC, MVP, MVVM, MVI, Clean Architecture.
- If any of these architectures is fulfilling your project requirements and you are following the standard coding guidelines and keeping your code clean, no architecture is bad.

#### Consider Using SVGs and WebP for images

- Supporting multiple resolutions are a sometimes nightmare to developers. Including multiple images for different resolutions also increases the project size.
- The solution is to use Vector Graphics such as SVG images or use WebP which can make a big difference in solving the image size problem by compressing lossless images.

#### Adding Third Party Libraries

- Try not to add Complete Third Party Libraries if it’s possible for you to extract specific methods or small no. of classes for your functionality.
- Just add those classes to your project and modify them accordingly.

#### Fix Memory Leaks 

- Its knowledge of the internals of the Android Framework gives it a unique ability to narrow down the cause of each leak, helping developers dramatically reduce OutOfMemoryError crashes.
- However, you can use use tools like [Leak Canary](https://square.github.io/leakcanary/)

#### Encrypted SharedPreferences

- Use [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences) instead of SharedPreferences for storing sensitive information like your auth tokens etc.

#### Write Unit tests for your feature.

* Unit tests help to fix bugs early in the development cycle and save costs.
* It helps the developers to understand the code base and enables them to make changes quickly
* Good unit tests serve as project documentation.

#### Use Proguard in your Apps

- It is quite easy to reverse engineer Android applications, so if you want to prevent this from happening, you should use [ProGuard](https://developer.android.com/studio/build/shrink-code) for its main function: obfuscation, a process of creating source code in a form that is hard for a human to understand(changing the name of classes and members).
- ProGuard has also two other important functions: shrinking which eliminates unused code and is obviously highly useful and also optimization.
- Optimization operates with Java bytecode, though, and since Android runs on Dalvik bytecode which is converted from Java bytecode, some optimizations won’t work so well. So you should be careful there.

#### Introduce Modularization in app
- Split your app module into different small modules, and give those modules as dependency to required modules 
- You will get benefit of faster builds while developing your app and reusable code. Later you can extend this to provide dynamic delivery module.

#### Log everything in DEBUG mode only

- We use logs to display useful information, errors, workflows or even to debug something.
- But, Every information that we log can be a potential source of security issues! So make sure you remove before the code goes live.

#### Use continuous integration

- Continuous integration systems let you automatically build and test your project every time you push updates to version control. Continuous integration also runs static code analysis tools, generates the APK files and distributes them.[Lint](https://developer.android.com/studio/write/lint.html) and [Checkstyle](http://checkstyle.sourceforge.net/) are tools that ensure the code quality while [Findbugs](http://findbugs.sourceforge.net/) looks for bugs in the code.
   
- There is a wide variety of continuous integration software which provide different features. Pricing plans might be for free if your project is open-sourced. [Jenkins](https://jenkins.io/) is a good option if you have a local server at your disposal, on the other hand [Travis CI](https://travis-ci.org/) is also a recommended choice if you plan to use a cloud-based continuous integration service.

#### Naming and Oragnising Resources

#### Naming 
Follow the convention of prefixing the type, as in `type_foo_bar.xml`. Examples: `fragment_contact_details.xml`, `view_primary_button.xml`, `activity_main.xml`.

#### Organizing layout XMLs.
- If you're unsure how to format a layout XML, the following convention may help.
- One attribute per line, indented by 4 spaces
- `android:id` as the first attribute always
- `android:layout_****` attributes at the top
- `style` attribute at the bottom
- Tag closer `/>` on its own line, to facilitate ordering and adding attributes.
- Rather than hard coding `android:text`, consider using [Designtime attributes](http://tools.android.com/tips/layout-designtime-attributes) available for Android Studio.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >

    <TextView
        android:id="@+id/name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:text="@string/name"
        style="@style/FancyText"
        />

    <include layout="@layout/reusable_part" />

</LinearLayout>
```

As a rule of thumb, attributes `android:layout_****` should be defined in the layout XML, while other attributes `android:****` should stay in a style XML. This rule has exceptions, but in general works fine. The idea is to keep only layout (positioning, margin, sizing) and content attributes in the layout files, while keeping all appearance details (colors, padding, font) in styles files.

The exceptions are:

- `android:id` should obviously be in the layout files
- `android:orientation` for a `LinearLayout` normally makes more sense in layout files
- `android:text` should be in layout files because it defines content
- Sometimes it will make sense to make a generic style defining `android:layout_width` and `android:layout_height` but by default these should appear in the layout files

#### Use styles 
Almost every project needs to properly use styles, because it is very common to have a repeated appearance for a view. At least you should have a common style for most text content in the application, for example:

```xml
<style name="ContentText">
    <item name="android:textSize">@dimen/font_normal</item>
    <item name="android:textColor">@color/basic_black</item>
</style>
```

Applied to TextViews:

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/price"
    style="@style/ContentText"
    />
```

You probably will need to do the same for buttons, but don't stop there yet. Go beyond and move a group of related and repeated `android:****` attributes to a common style.

#### Split a large style file into other files
You don't need to have a single `styles.xml` file. Android SDK supports other files out of the box, there is nothing magical about the name `styles`, what matters are the XML tags `<style>` inside the file. Hence you can have files `styles.xml`, `styles_home.xml`, `styles_item_details.xml`, `styles_forms.xml`. Unlike resource directory names which carry some meaning for the build system, filenames in `res/values` can be arbitrary.

#### `colors.xml` is a color palette 
There should be nothing in your `colors.xml` other than a mapping from a color name to an RGBA value. This helps avoid repeating RGBA values and as such will make it easy to change or refactor colors, and also will make it explicit how many different colors are being used. Normally for a aesthetic UI, it is important to reduce the variety of colors being used.
 
*So, don't define your colors.xml like this:*

```xml
<resources>
    <color name="button_foreground">#FFFFFF</color>
    <color name="button_background">#2A91BD</color>
</resources>    
```

Instead, do this:

```xml
<resources>
    <!-- grayscale -->
    <color name="white">#FFFFFF</color>
   
    <!-- basic colors -->
    <color name="blue">#2A91BD</color>
</resources>
```

Ask the designer of the application for this palette. The names do not need to be plain color names as "green", "blue", etc. Names such as "brand_primary", "brand_secondary", "brand_negative" are totally acceptable as well.

By referencing the color palette from your styles allows you to abstract the underlying colors from their usage in the app, as per:

- `colors.xml` - defines only the color palette.
- `styles.xml` - defines styles which reference the color palette and reflects the color usage. (e.g. the button foreground is white).
- `activity_main.xml` - references the appropriate style in `styles.xml` to color the button.

If needed, even further separation between underlying colors and style usage can be achieved by defined an additional color resource file which references the color palette. As per:

```xml
<color name="button_foreground">@color/white</color> 
<color name="button_background">@color/blue</color> 
```

Then in styles.xml:

```xml
<style name="AcceptButton">
    <item name="android:foreground">@color/button_foreground</item>
    <item name="android:background">@color/button_background</item>
</style>
```

This approach offers improved color refactoring and more stable style definitions when multiple related styles share similar color and usage properties. However, it comes at the cost of maintaining another set of color mappings. 

<a name="dimensxml"></a>
**Treat dimens.xml like colors.xml.** You should also define a "palette" of typical spacing and font sizes, for basically the same purposes as for colors. A good example of a dimens file:

```xml
<resources>

    <!-- font sizes -->
    <dimen name="font_larger">22sp</dimen>
    <dimen name="font_large">18sp</dimen>
    <dimen name="font_normal">15sp</dimen>
    <dimen name="font_small">12sp</dimen>

    <!-- typical spacing between two views -->
    <dimen name="spacing_huge">40dp</dimen>
    <dimen name="spacing_large">24dp</dimen>
    <dimen name="spacing_normal">14dp</dimen>
    <dimen name="spacing_small">10dp</dimen>
    <dimen name="spacing_tiny">4dp</dimen>

    <!-- typical sizes of views -->
    <dimen name="button_height_tall">60dp</dimen>
    <dimen name="button_height_normal">40dp</dimen>
    <dimen name="button_height_short">32dp</dimen>

</resources>
```

You should use the `spacing_****` dimensions for layouting, in margins and paddings, instead of hard-coded values, much like strings are normally treated. This will give a consistent look-and-feel, while making it easier to organize and change styles and layouts.

**strings.xml**

Name your strings with keys that resemble namespaces, and don't be afraid of repeating a value for two or more keys. Languages are complex, so namespaces are necessary to bring context and break ambiguity.

**Bad**
```xml
<string name="network_error">Network error</string>
<string name="call_failed">Call failed</string>
<string name="map_failed">Map loading failed</string>
```

**Good**
```xml
<string name="error_message_network">Network error</string>
<string name="error_message_call">Call failed</string>
<string name="error_message_map">Map loading failed</string>
```

Don't write string values in all uppercase. Stick to normal text conventions (e.g., capitalize first character). If you need to display the string in all caps, then do that using for instance the attribute [`textAllCaps`](http://developer.android.com/reference/android/widget/TextView.html#attr_android:textAllCaps) on a TextView.

**Bad**
```xml
<string name="error_message_call">CALL FAILED</string>
```

**Good**
```xml
<string name="error_message_call">Call failed</string>
```
