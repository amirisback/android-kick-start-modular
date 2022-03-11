plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.compose") version DependencyGradle.COMPOSE_MULTIPLATFORM_VERSION
    id("kotlin-kapt")
}

android {

    def versionMajor = 1
    def versionMinor = 0
    def versionPatch = 0

    def appDomain = "com"
    def appDevConsole = "frogobox"
    def appName = "movie"

    def projectApplicationId = "$appDomain.$appDevConsole.$appName"
    def projectVersionCode = (versionMajor * 100) + (versionMinor * 10) + (versionPatch * 1)
    def projectVersionName = "$versionMajor.$versionMinor.$versionPatch"

    // Declaration app name
    def nameApp = "Catalogue Movie"
    def nameAppLower = nameApp.toLowerCase()

    // Declaration apk / aab name
    def nameAPK = nameAppLower.replace(" ", "-")

    // Declaration Room DB Name
    def nameDB = '"catalogue_movie.db"'

    // Declaration admob id for debug
    def debugAdmobPublisherId = "ca-app-pub-3940256099942544~3347511713"
    def debugAdmobBanner = "ca-app-pub-3940256099942544/6300978111"
    def debugAdmobInterstitial = "ca-app-pub-3940256099942544/1033173712"
    def debugAdmobInterstitialVideo = "ca-app-pub-3940256099942544/8691691433"
    def debugAdmobRewarded = "ca-app-pub-3940256099942544/5224354917"
    def debugAdmobRewardedInterstitial = "ca-app-pub-3940256099942544/5354046379"
    def debugAdmobNativeAdvanced = "ca-app-pub-3940256099942544/2247696110"
    def debugAdmobNativeAdvancedVideo = "ca-app-pub-3940256099942544/1044960115"

    // Declaration admob id for release
    def releaseAdmobPublisherId = ""
    def releaseAdmobBanner = ""
    def releaseAdmobInterstitial = ""
    def releaseAdmobInterstitialVideo = ""
    def releaseAdmobRewarded = ""
    def releaseAdmobRewardedInterstitial = ""
    def releaseAdmobNativeAdvanced = ""
    def releaseAdmobNativeAdvancedVideo = ""

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    compileSdk projectCompileSdkVersion

    defaultConfig {
        applicationId projectApplicationId
        minSdk projectMinSdkVersion
        targetSdk projectTargetSdkVersion
        versionCode projectVersionCode
        versionName projectVersionName

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        // Declaration build config
        buildConfigField("String", "DATABASE_NAME", nameDB)

        // Declaration apps name debug mode
        def debugAttribute = " Development"
        def nameAppDebug = nameApp + debugAttribute

        // Naming APK // AAB
        setProperty("archivesBaseName", nameAPK + versionName + "#" + versionCode + "-"
                + new Date().format("ddMMyy"))
        resConfigs "en", "id"


        // Inject app name for debug
        resValue "string", "app_name", nameAppDebug

        // Inject admob id for debug
        resValue "string", "admob_publisher_id", debugAdmobPublisherId
        resValue "string", "admob_banner", debugAdmobBanner
        resValue "string", "admob_interstitial", debugAdmobInterstitial
        resValue "string", "admob_interstitial_video", debugAdmobInterstitialVideo
        resValue "string", "admob_rewarded", debugAdmobRewarded
        resValue "string", "admob_rewarded_interstitial", debugAdmobRewardedInterstitial
        resValue "string", "admob_native_advanced", debugAdmobNativeAdvanced
        resValue "string", "admob_native_advanced_video", debugAdmobNativeAdvancedVideo

    }

    signingConfigs {
        release {
            // You need to specify either an absolute path or include the
            // keystore file in the same directory as the build.gradle file.
            storeFile file("frogoboxdev.jks")
            storePassword "cronoclez"
            keyAlias "frogobox"
            keyPassword "xeonranger"
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"

            // Generated Signed APK / AAB
            signingConfig signingConfigs.release

            // Inject app name for release
            resValue "string", "app_name", nameApp

            // Inject admob id for release
            resValue "string", "admob_publisher_id", releaseAdmobPublisherId
            resValue "string", "admob_banner", releaseAdmobBanner
            resValue "string", "admob_interstitial", releaseAdmobInterstitial
            resValue "string", "admob_interstitial_video", releaseAdmobInterstitialVideo
            resValue "string", "admob_rewarded", releaseAdmobRewarded
            resValue "string", "admob_rewarded_interstitial", releaseAdmobRewardedInterstitial
            resValue "string", "admob_native_advanced", releaseAdmobNativeAdvanced
            resValue "string", "admob_native_advanced_video", releaseAdmobNativeAdvancedVideo

        }
    }
}

dependencies {

    implementation project(":base")

    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    implementation "androidx.appcompat:appcompat:1.4.1"
    implementation "androidx.core:core-ktx:1.7.0"
    implementation "androidx.preference:preference-ktx:1.2.0"
    implementation "androidx.constraintlayout:constraintlayout:2.1.3"
    implementation "androidx.legacy:legacy-support-v4:1.0.0"

    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha03"
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"

    implementation "androidx.work:work-runtime-ktx:2.7.1"

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