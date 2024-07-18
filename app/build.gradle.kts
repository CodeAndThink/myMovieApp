plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.truong.movieapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.truong.movieapplication"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    testOptions{
        unitTests{
            isIncludeAndroidResources = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.ui.desktop)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.testng)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Room components
    implementation(libs.androidx.room.runtime)
    androidTestImplementation(project(":app"))
    androidTestImplementation(project(":app"))
    kapt(libs.androidx.room.compiler)

    //Glide
    implementation (libs.glide)
    kapt (libs.compiler)

    //Parcelize
    implementation (libs.kotlin.parcelize.runtime)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    //Reflect
    implementation (libs.kotlin.reflect)

    //Dagger

    //Test
    testImplementation (libs.androidx.core.testing)
    testImplementation(libs.mockk.v11311)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation (libs.mockito.core)
    testImplementation(libs.androidx.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation (libs.androidx.junit.v121)
    testImplementation (libs.robolectric)
    testImplementation (libs.mockwebserver)
    testImplementation (libs.retrofit.mock)
    testImplementation (libs.mockito.kotlin)
    androidTestImplementation (libs.androidx.espresso.core.v361)
    androidTestImplementation (libs.androidx.espresso.intents)
    androidTestImplementation (libs.androidx.espresso.contrib)
    testImplementation (libs.androidx.room.testing)
    androidTestImplementation (libs.hamcrest.library)
}