object Depends {
    const val androidx = "androidx.core:core-ktx:1.7.0"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
    const val material = "com.google.android.material:material:1.4.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.6"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
    const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"

    //compose
    const val compose = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"
    const val composeAnimation = "androidx.compose.animation:animation:${Versions.compose}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeFoundationLayout =
        "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeMaterialIcon =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiTool = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiPreview = "androidx.compose.ui:ui-tooling-preview::${Versions.compose}"
    const val composeTest = "androidx.compose.ui:ui-test-junit4::${Versions.compose}"

    //room
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"
    const val roomCompiler =
        "androidx.room:room-compiler:${Versions.room}"// To use Kotlin Symbolic Processing (KSP)

    const val gson = "com.google.code.gson:gson:2.8.9"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    //test
    const val junit = "junit:junit:${Versions.junit}"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    const val robolectric = "org.robolectric:robolectric:4.6"

    //androidX test
    // Core library
    const val androidxTestCore = "androidx.test:core:1.0.0"

    // AndroidJUnitRunner and JUnit Rules
    const val androidXTestJunit = "androidx.test.ext:junit:1.1.3"
    const val androidXRunner = "androidx.test:runner:${Versions.androidXTest}"
    const val androidXRules = "androidx.test:rules:${Versions.androidXTest}"
}

object Versions {
    const val kotlin = "1.5.21"
    const val compose = "1.0.1"
    const val activityCompose = "1.3.1"
    const val viewModelCompose = "2.4.0"
    const val room = "2.3.0"
    const val junit = "4.13.2"
    const val coroutines = "1.5.0"
    const val androidXTest = "1.1.0"
}

object Android {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 144
    const val versionName = "1.7.60"
}