My Cocktail Bar
===============

![My Cocktail Bar](./screenshots/cocktails.jpg "My Cocktail Bar")

A cocktails recipes application intended to demonstrate Android best development practices using the latest 
technologies and trends such as Android Jetpack, Compose, unidirectional data flow, etc.

As this app is used for practicing purposes it does not offer any backward support for older version.
In addition this app is a constant work in progress where new features, SDKs and APIs will be added over times.

Introduction
------------
This app is utilising an open API called [The Cocktails DB](https://www.thecocktaildb.com/api.php) in order to 
provide different cocktails recipes and ingredients.

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
    * [AppCompat][1] - Degrade gracefully on older versions of Android.
    * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
* [Architecture][3] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
    * [Lifecycles][4] - Create a UI that automatically responds to lifecycle events.
    * [Navigation][5] - Handle everything needed for in-app navigation.
    * [Room][6] - Access your app's SQLite database with in-app objects and compile-time checks.
    * [ViewModel][7] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
      asynchronous tasks for optimal execution.
* [UI][8] - Details on why and how to use UI Components in your apps - together or separate
    * [Compose][9] - A basic unit of composable UI.
* Third party and miscellaneous libraries
    * [Glide][10] for image loading
    * [Hilt][11]: for [dependency injection][12]
    * [Kotlin Coroutines][13] for managing background threads with simplified code and reducing needs for callbacks
    * [Kotlin Flows][14] for providing consistent unidirectional data flow between the different app layers in a non 
      platform dependent way.
    * [Retrofit][15] A type-safe HTTP client for Android and Java

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[3]: https://developer.android.com/jetpack/arch/
[4]: https://developer.android.com/topic/libraries/architecture/lifecycle
[5]: https://developer.android.com/topic/libraries/architecture/navigation/
[6]: https://developer.android.com/topic/libraries/architecture/room
[7]: https://developer.android.com/topic/libraries/architecture/viewmodel
[8]: https://developer.android.com/guide/topics/ui
[9]: https://developer.android.com/jetpack/compose
[10]: https://bumptech.github.io/glide/
[11]: https://developer.android.com/training/dependency-injection/hilt-android
[12]: https://developer.android.com/training/dependency-injection
[13]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[14]: https://developer.android.com/kotlin/flow
[15]: https://github.com/square/retrofit
