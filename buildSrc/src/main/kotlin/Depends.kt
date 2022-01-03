import Versions.activityCompact

object Depends {
    const val androidx = "androidx.core:core-ktx:1.7.0"
    const val appcompat = "androidx.appcompat:appcompat:${activityCompact}"
    const val material = "com.google.android.material:material:1.4.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.6"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
    const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0"
    const val livecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"

    //compose
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiTool =
        "androidx.compose.ui:ui-tooling:${Versions.compose}" //debugImplementation for preview
    const val composeActivity = "androidx.activity:activity-compose:$activityCompact"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeAnimation = "androidx.compose.animation:animation:${Versions.compose}"

    const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeFoundationLayout =
        "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val composeMaterialIconCore =
        "androidx.compose.material:material-icons-core:${Versions.compose}"
    const val composeMaterialIconExt =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val composeTest = "androidx.compose.ui:ui-test-junit4::${Versions.compose}"

    //room
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"
    const val roomCompiler =
        "androidx.room:room-compiler:${Versions.room}"// To use Kotlin Symbolic Processing (KSP)

    //hilt
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"

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
    const val hilt = "2.3.7"
    const val jvm = "1.8"
    const val kotlin = "1.5.31"
    const val compose = "1.0.5"
    const val activityCompact = "1.3.1"
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
    const val versionCode = 1
    const val versionName = "1.0.0"
}