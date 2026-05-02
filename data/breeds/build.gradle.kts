plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}


dependencies {
    ksp(libs.moshi.kotlin.codegen)

    api(project(":data:core"))
    implementation(project(":domain:breeds"))
}
