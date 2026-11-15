pluginManagement {
    repositories {
        google()
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
rootProject.name = "CleanLauncher"

// 모듈 네 개. 화살표는 "안다"는 뜻
//   app  ->  feature:home  ->  core:domain
//   app  ->  core:data     ->  core:domain
include(":app")
include(":feature:home")
include(":core:domain")
include(":core:data")
