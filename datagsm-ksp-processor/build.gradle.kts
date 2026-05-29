import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "team.themoment"

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:${plugin.PluginVersions.KSP_VERSION}")
    implementation(dependency.Dependencies.KOTLIN_POET)
    implementation(dependency.Dependencies.KOTLIN_POET_KSP)
}
