// Common plugins, dependencies, and task config come from the service-module block
// in the root build.gradle.kts. Only module-specific dependencies live here.
dependencies {
    "implementation"(dependency.Dependencies.SPRING_MAIL)

    "implementation"(dependency.Dependencies.JJWT)
    "runtimeOnly"(dependency.Dependencies.JJWT_IMPL)
    "runtimeOnly"(dependency.Dependencies.JJWT_JACKSON)

    "implementation"(dependency.Dependencies.POI)
    "implementation"(dependency.Dependencies.POI_OOXML)
}
