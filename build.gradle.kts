// Top-level build file where you can add configuration options common to all sub-projects/modules.


    /*
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.kotlin.kapt") apply false // Cambiar a esta versión correcta

     */

    // Top-level build file where you can add configuration options common to all sub-projects/modules.
    plugins {
        id("com.android.application") version "8.8.0" apply false
        id("org.jetbrains.kotlin.android") version "1.9.10" apply false
        id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
    }

