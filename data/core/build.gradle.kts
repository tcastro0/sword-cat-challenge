plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tcastro.swordcatchallenge.data.core"
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


dependencies {
    api(libs.bundles.network)
    ksp(libs.moshi.kotlin.codegen)

    api(platform(libs.koin.bom))
    api(libs.koin.android)
    api(libs.kotlinx.coroutines.android)

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
}


tasks.whenTaskAdded {
    if (name.contains("AndroidTest")) enabled = false
}