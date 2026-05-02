plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tcastro.swordcatchallenge.data.breeds"
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

dependencies {
    ksp(libs.moshi.kotlin.codegen)

    api(project(":data:core"))
    implementation(project(":domain:breeds"))

    testImplementation(libs.bundles.integration.testing)

}

tasks.whenTaskAdded {
    if (name.contains("AndroidTest")) enabled = false
}