plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }

}
android {
    namespace = "com.tcastro.swordcatchallenge.data.core"
    compileSdk = 36
}


dependencies {
    api(libs.bundles.network)
    ksp(libs.moshi.kotlin.codegen)

    api(platform(libs.koin.bom))
    api(libs.koin.android)
    api(libs.kotlinx.coroutines.android)
}

