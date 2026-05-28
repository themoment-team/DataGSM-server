plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    `maven-publish`
}

group = "team.themoment"
version = (findProperty("SHARED_VERSION") as? String)
    ?: System.getenv("SHARED_VERSION")
    ?: "local"

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.js.ExperimentalJsExport")
    }

    jvm {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
                }
            }
        }
    }

    js(IR) {
        outputModuleName.set("datagsm-shared")
        nodejs()
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
        compilations.named("main") {
            packageJson {
                name = "@themoment-team/datagsm-shared"
            }
        }
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(
                project(":datagsm-common")
                    .layout.buildDirectory
                    .dir("generated/kmp-export/main/kotlin"),
            )
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-Xes-long-as-bigint")
    }
}

listOf("compileKotlinJvm", "compileKotlinJs").forEach { taskName ->
    tasks.named(taskName) {
        dependsOn(":datagsm-common:kspKotlin")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/themoment-team/datagsm-server")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
