rootProject.name = "datagsm-server"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        exclusiveContent {
            forRepository {
                ivy {
                    name = "NodeDistributions"
                    url = uri("https://nodejs.org/dist")
                    patternLayout {
                        artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
                    }
                    metadataSources { artifact() }
                    content { includeModule("org.nodejs", "node") }
                }
            }
            filter { includeGroup("org.nodejs") }
        }
        exclusiveContent {
            forRepository {
                ivy {
                    name = "YarnDistributions"
                    url = uri("https://github.com/yarnpkg/yarn/releases/download")
                    patternLayout {
                        artifact("v[revision]/[artifact](-v[revision]).[ext]")
                    }
                    metadataSources { artifact() }
                    content { includeModule("com.yarnpkg", "yarn") }
                }
            }
            filter { includeGroup("com.yarnpkg") }
        }
    }
}

buildCache {
    local {
        directory = file("$rootDir/.gradle/build-cache")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("datagsm-ksp-annotations")
include("datagsm-ksp-processor")
include("datagsm-shared")
include("datagsm-common")
include("datagsm-oauth-authorization")
include("datagsm-openapi")
include("datagsm-web")
include("datagsm-oauth-userinfo")
