import com.mingg.buildlogic.etc.configureAndroidHilt

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.library")
}

configureAndroidHilt()

dependencies {
    implementation(project(":domain"))
}
