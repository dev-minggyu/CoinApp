package com.mingg.buildlogic.etc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidHilt() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("org.jetbrains.kotlin.kapt")
    }

    dependencies {
        implementation(libs.findLibrary("hilt.core").get())
        kapt(libs.findLibrary("hilt.compiler").get())

        implementation(libs.findLibrary("hilt.android").get())
        kapt(libs.findLibrary("hilt.android.compiler").get())
    }
}

internal class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidHilt()
        }
    }
}