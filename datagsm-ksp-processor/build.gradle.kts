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
    implementation(project(":datagsm-ksp-annotations"))
    implementation("com.google.devtools.ksp:symbol-processing-api:${plugin.PluginVersions.KSP_VERSION}")
    implementation("com.squareup:kotlinpoet:2.2.0")
    implementation("com.squareup:kotlinpoet-ksp:2.2.0")
}
