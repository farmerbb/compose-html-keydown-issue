pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String).apply(false)
        id("org.jetbrains.kotlin.plugin.compose").version(extra["kotlin.version"] as String).apply(false)
    }
}

rootProject.name = "compose-multiplatform-html-library-template"
