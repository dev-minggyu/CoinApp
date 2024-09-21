plugins {
    id("android.library")
    id("kotlinx-serialization")
    id("android.room")
}

android {
    namespace = "com.mingg.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.websockets)

    implementation(libs.kotlin.serialization)
}