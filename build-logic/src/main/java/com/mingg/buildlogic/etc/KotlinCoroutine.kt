package com.mingg.buildlogic.etc

import gradle.kotlin.dsl.accessors._2fb5859a04200edaf14b854c40b2e363.testImplementation
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureCoroutineKotlin() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        implementation(libs.findLibrary("coroutines.core").get())
        testImplementation(libs.findLibrary("coroutines.test").get())
    }
}
