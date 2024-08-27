import com.mingg.buildlogic.etc.configureAndroidApplication
import com.mingg.buildlogic.etc.configureAndroidHilt

plugins {
    id("com.android.library")
}

configureAndroidApplication()
configureAndroidHilt()