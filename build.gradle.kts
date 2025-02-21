plugins {
    id("com.android.application") version "8.8.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false // 🔥 Plugin de Google Services
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
