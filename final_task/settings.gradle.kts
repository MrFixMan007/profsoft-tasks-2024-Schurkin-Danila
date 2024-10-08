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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Profsoft-2024-Final-Task"
include(":app")
include(":core:utils")
include(":core:common")
include(":core:ui")
include(":network")
include(":feature_login")
include(":feature_home_screen")
include(":core:navigation:unauthenticated")
include(":core:navigation:authenticated")
include(":database")
include(":feature_details")
