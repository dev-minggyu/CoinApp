plugins {
    id("android.library")
    id("android.room")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.database"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlin.serialization)
}