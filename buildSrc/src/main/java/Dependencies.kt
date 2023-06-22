object Dependencies {

    //Core
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtxVersion}" }
    val platformKotlin by lazy { "org.jetbrains.kotlin:kotlin-bom:${Versions.platformKotlinBomVersion}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}" }
    val compose by lazy { "androidx.activity:activity-compose:${Versions.activityComposeVersion}" }
    val platformCompose by lazy { "androidx.compose:compose-bom:${Versions.platformComposeBomVersion}" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val composeUiPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val material3 by lazy { "androidx.compose.material3:material3:${Versions.material3Version}" }
    val foundation by lazy { "androidx.compose.foundation:foundation:${Versions.foundationVersion}" }


    //Test
    val junit by lazy { "junit:junit:${Versions.junitVersion}" }
    val junitTest by lazy { "androidx.test.ext:junit:${Versions.junitTestVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}" }
    val composeTest by lazy { "androidx.compose:compose-bom:${Versions.platformComposeBomTestVersion}" }
    val composeTestJunit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeTestTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val composeTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }

    //Viewmodel
    val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewmodelComposeVersion}" }
    val ktxViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtxVersion}" }

    //Coroutines
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCoreVersion}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}" }

    //Retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val gsonConvertor by lazy { "com.squareup.retrofit2:converter-gson:${Versions.converterGsonVersion}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}" }
    val okHttpInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpInterceptorVersion}" }

    //Navigation
    val navigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNavigationVersion}" }

    //Splash API
    val splash by lazy { "androidx.core:core-splashscreen:${Versions.splashScreenVersion}" }

    //Dagger - Hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompilerVersion}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompilerVersion}" }

    //Datastore
    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.datastoreVersion}" }

    //Numberpicker
    val numberPicker by lazy { "com.chargemap.compose:numberpicker:${Versions.numberpickerVersion}" }
}