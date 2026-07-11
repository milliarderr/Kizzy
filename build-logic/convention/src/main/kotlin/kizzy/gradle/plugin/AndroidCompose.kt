package kizzy.gradle.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    // Kotlin 2.x: compose compiler is a separate Kotlin plugin, not composeOptions
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        // composeOptions.kotlinCompilerExtensionVersion is no longer needed in Kotlin 2.x
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        val composeBom = libs.findLibrary("compose.bom").get()
        // BOM controls all androidx.compose.* versions
        add("implementation", platform(composeBom))
        add("androidTestImplementation", platform(composeBom))

        add("implementation", libs.findLibrary("compose.ui").get())
        add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())
        add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())
    }
}
