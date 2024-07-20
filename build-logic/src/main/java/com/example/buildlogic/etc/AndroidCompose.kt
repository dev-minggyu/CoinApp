package com.example.buildlogic.etc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            android {
                buildFeatures.compose = true
                dependencies {
                    val bom = libs.findLibrary("androidx-compose-bom").get()
                    implementation(platform(bom))
                    androidTestImplementation(platform(bom))
                }
            }
        }
    }
}