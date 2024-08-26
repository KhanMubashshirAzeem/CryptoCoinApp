plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.cryptocoinapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cryptocoinapp"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Smooth bottom bar from github
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    // Animated loader from github names: Spinkit
    implementation("com.github.ybq:Android-SpinKit:1.4.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")

    //Json
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Lifecycle coroutine
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")


}