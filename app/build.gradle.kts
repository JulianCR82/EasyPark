plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.easypark"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easypark"
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //libreria lottie
    implementation ("com.airbnb.android:lottie:6.5.2")

    //libreria iText
    implementation ("com.itextpdf:itextg:5.5.10")

    implementation ("androidx.core:core:1.6.0")
}