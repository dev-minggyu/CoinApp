package com.ming.buildlogic.etc

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

fun Project.configureAndroidApplication() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    with(pluginManager) {
        apply("org.jetbrains.kotlin.android")
    }

    android {
        compileSdkVersion(Versions.COMPILE_SDK)

        defaultConfig {
            versionCode = Versions.VERSION_CODE
            versionName = Versions.VERSION_NAME
            minSdk = Versions.MIN_SDK
            targetSdk = Versions.TARGET_SDK
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        dependencies {
            coreLibraryDesugaring(libs.findLibrary("android.desugar.jdk").get())

            implementation(libs.findLibrary("test.junit").get())
            implementation(libs.findLibrary("test.junit.extension").get())
            implementation(libs.findLibrary("test.esspresso.core").get())
        }
    }
}
