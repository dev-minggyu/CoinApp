import com.example.buildlogic.etc.configureCoroutineKotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

configureCoroutineKotlin()

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(libs.findLibrary("javax.inject").get())
}