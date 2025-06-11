import com.ming.buildlogic.etc.configureAndroidApplication
import com.ming.buildlogic.etc.configureAndroidHilt

plugins {
    id("com.android.library")
}

configureAndroidApplication()
configureAndroidHilt()