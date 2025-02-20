plugins {
    /*
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Se usa id() en vez de alias()
*/
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.simarropopandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.simarropopandroid"
        minSdk = 24


        android {
            namespace = "com.example.simarropopandroid"
            compileSdk = 35

            defaultConfig {
                applicationId = "com.example.simarropopandroid"
                minSdk = 24
                targetSdk = 35
                versionCode = 1
                versionName = "1.0"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            kotlinOptions {
                jvmTarget = "11"
            }
            buildFeatures {
                viewBinding = true
            }
        }

        dependencies {
            // Implementaciones necesarias para el proyecto
            implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
            implementation("androidx.navigation:navigation-ui-ktx:2.7.2")
            implementation("androidx.room:room-ktx:2.5.2")
            implementation("com.google.android.material:material:1.9.0")
            implementation("androidx.cardview:cardview:1.0.0")
            implementation("com.airbnb.android:lottie:5.2.0")
            implementation("com.squareup.retrofit2:retrofit:2.9.0")
            implementation("com.squareup.retrofit2:converter-gson:2.9.0")
            implementation("com.github.bumptech.glide:glide:4.15.1")
            annotationProcessor("com.github.bumptech.glide:compiler:4.15.1") // Fix de Glide annotationProcessor

            // Room Database
            val roomVersion = "2.6.1"
            implementation("androidx.room:room-runtime:$roomVersion")
            ksp("androidx.room:room-compiler:$roomVersion") // Habilita KAPT para Room

            // Dependencias generales
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.material)
            implementation(libs.androidx.activity)
            implementation(libs.androidx.constraintlayout)
            implementation(libs.androidx.coordinatorlayout)

            // Testing
            testImplementation(libs.junit)
            androidTestImplementation(libs.androidx.junit)
            androidTestImplementation(libs.androidx.espresso.core)
        }
    }
}
