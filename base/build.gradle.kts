plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}


android {

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding true
    }

    compileSdk projectCompileSdkVersion

    defaultConfig {

        minSdk projectMinSdkVersion
        targetSdk projectTargetSdkVersion

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"


        buildConfigField("String", "TMDB_API_KEY", '"5e922c3d4b1b0e96fffcc6b0b395878f"')
        buildConfigField("String", "TMDB_BASE_URL", '"https://api.themoviedb.org/"')
        buildConfigField("String", "TMDB_BASE_LANG", '"en-US"')

        buildConfigField("String", "TMDB_PATH_URL_IMAGE", '"https://image.tmdb.org/t/p/w342/"')

        buildConfigField("String", "TMDB_URL_MOVIE", '"3/discover/movie?api_key=5e922c3d4b1b0e96fffcc6b0b395878f&language=en-US"')
        buildConfigField("String", "TMDB_URL_TV", '"3/discover/tv?api_key=5e922c3d4b1b0e96fffcc6b0b395878f&language=en-US"')

        buildConfigField("String", "TMDB_URL_SEARCH_MOVIE", '"3/search/movie"')
        buildConfigField("String", "TMDB_URL_SEARCH_TV_SHOW", '"3/search/tv"')

        buildConfigField("String", "TMDB_URL_RELEASE_BY_DATE", '"3/discover/movie?api_key={API KEY}&primary_release_date.gte={TODAY DATE}&primary_release_date.lte={TODAY DATE}"')

    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }

}

dependencies {

    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    implementation "androidx.appcompat:appcompat:1.4.1"
    implementation "androidx.core:core-ktx:1.7.0"
    implementation "androidx.preference:preference-ktx:1.2.0"
    implementation "androidx.constraintlayout:constraintlayout:2.1.3"
    implementation "androidx.legacy:legacy-support-v4:1.0.0"

    implementation "androidx.work:work-runtime-ktx:2.7.1"

    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha03"
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
    implementation "androidx.room:room-runtime:2.4.2"
    implementation "androidx.room:room-ktx:2.4.2"
    implementation "androidx.room:room-rxjava2:2.4.2"
    implementation "androidx.room:room-guava:2.4.2"

    implementation "com.google.code.gson:gson:2.9.0"
    implementation "com.google.android.material:material:1.5.0"
    implementation "com.google.android.gms:play-services-ads:20.6.0"

    implementation "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
    implementation "com.squareup.okhttp3:logging-interceptor:4.9.3"
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    implementation "com.squareup.picasso:picasso:2.71828"

    implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
    implementation "io.reactivex.rxjava2:rxjava:2.2.21"

    implementation "com.github.amirisback:frogo-recycler-view:4.0.4"
    implementation "com.github.amirisback:frogo-ui-kit:2.1.0"
    implementation "com.github.bumptech.glide:glide:4.12.0"

    implementation "com.facebook.stetho:stetho:1.5.1"
    implementation "com.readystatesoftware.chuck:library:1.1.0"

    // Koin dependency Injection
    implementation "org.koin:koin-androidx-scope:2.1.5"
    implementation "org.koin:koin-android:2.1.5"
    implementation "org.koin:koin-androidx-viewmodel:2.1.5"

    api "com.google.dagger:dagger:2.38.1"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"

    kapt "androidx.lifecycle:lifecycle-compiler:2.5.0-alpha03"
    kapt "androidx.room:room-compiler:2.4.2"
    kapt "com.google.dagger:dagger-compiler:2.37"
    kapt "com.github.bumptech.glide:compiler:4.12.0"

    testImplementation "junit:junit:4.13.2"
    testImplementation "androidx.room:room-testing:2.4.2"

    androidTestImplementation "androidx.room:room-testing:2.4.2"
    androidTestImplementation "androidx.arch.core:core-testing:2.1.0"
    androidTestImplementation "androidx.test:runner:1.4.0"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.4.0"

}
