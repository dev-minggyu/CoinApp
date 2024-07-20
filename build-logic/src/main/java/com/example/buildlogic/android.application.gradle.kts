import com.example.buildlogic.etc.configureAndroidApplication
import com.example.buildlogic.etc.configureAndroidHilt

plugins {
    id("com.android.application")
}

configureAndroidApplication()
configureAndroidHilt()