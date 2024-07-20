plugins {
    id("android.library")
    id("android.hilt")
}

android {
    namespace = "com.example.repository"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
}
