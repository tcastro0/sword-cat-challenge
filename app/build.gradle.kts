import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.tcastro.swordcatchallenge"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.tcastro.swordcatchallenge"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "CAT_API_URL",
            "\"https://api.thecatapi.com/v1/\""
        )

        val apiKey = gradleLocalProperties(rootDir, providers)
            .getProperty("CAT_KEY") ?: ""
        buildConfigField(
            "String",
            "CAT_KEY",
            "\"$apiKey\""
        )

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }

}
kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.bundles.android)
    implementation(platform(libs.androidx.compose.bom))

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.android)

    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.uitesting)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(project(":core:ui"))
    implementation(project(":data:core"))
    implementation(project(":data:breeds"))
    implementation(project(":data:favourites"))
    implementation(project(":domain:breeds"))
    implementation(project(":domain:favourites"))
    implementation(project(":feature:breeds"))
    implementation(project(":feature:favourites"))
}