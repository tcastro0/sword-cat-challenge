plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tcastro.swordcatchallenge.data.favourites"
    compileSdk = 36
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}
kotlin {
    jvmToolchain(21)
}


tasks.whenTaskAdded {
    if (name.contains("AndroidTest")) enabled = false
}