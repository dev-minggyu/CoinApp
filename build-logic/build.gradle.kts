plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "android.compose"
            implementationClass = "com.mingg.buildlogic.etc.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "android.hilt"
            implementationClass = "com.mingg.buildlogic.etc.AndroidHiltPlugin"
        }
        register("androidRoom") {
            id = "android.room"
            implementationClass = "com.mingg.buildlogic.etc.AndroidRoomPlugin"
        }
    }
}