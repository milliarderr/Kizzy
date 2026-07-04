package kizzy.gradle.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("compose.bom").get()
            add("implementation", platform(bom))
            add("debugImplementation", platform(bom))
            add("implementation", libs.findLibrary("compose.ui").get())
            add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())
            add("implementation", libs.findLibrary("compose-ui.tooling.preview").get())
        }
    }
}
