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


    implementation(libs.bundles.network)
    ksp(libs.moshi.kotlin.codegen)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}

