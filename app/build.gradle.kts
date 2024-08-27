plugins {
    id("android.application")
    id("android.hilt")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.coinapp"

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = "coinapp1"
            storeFile = file("../../CoinApp_Sign/coinapp1.jks")
            storePassword = "coinapp1"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:network"))
    implementation(project(":core:repository"))

    implementation(libs.kotlin.serialization)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.extension)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.preference)

    implementation(libs.google.android.material)

    implementation(libs.mpchart)

    implementation(libs.leakcanary.android)

    implementation(libs.sdp)
    implementation(libs.ssp)

    implementation(libs.test.junit)
    implementation(libs.test.junit.extension)
    implementation(libs.test.esspresso.core)
}