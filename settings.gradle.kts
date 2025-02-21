pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT) // ✅ Permite repos en build.gradle.kts
    repositories {
        google()
        mavenCentral()
        jcenter() // Solo si realmente lo necesitas
    }
}

rootProject.name = "SimarroPopAndroid"
include(":app")
