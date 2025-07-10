import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {

    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {

                implementation(compose.desktop.currentOs)

                implementation(libs.compose.material3)
                implementation(libs.compose.material.icons.core)
                implementation(libs.compose.material.icons.extended)

                implementation(kotlinx.datetime)

                // Libraries
                implementation(project(":composepreferences:core"))
                implementation(project(":composepreferences:modules:screen:bool"))
                implementation(project(":composepreferences:modules:screen:button"))
                implementation(project(":composepreferences:modules:screen:input"))
                implementation(project(":composepreferences:modules:screen:color"))
                implementation(project(":composepreferences:modules:screen:date"))
                implementation(project(":composepreferences:modules:screen:time"))
                implementation(project(":composepreferences:modules:screen:list"))
                implementation(project(":composepreferences:modules:screen:number"))
                implementation(project(":composepreferences:modules:kotpreferences"))

                // KotPreferences
                implementation(deps.kotpreferences.core)
                implementation(deps.kotpreferences.storage.datastore)
                implementation(deps.kotpreferences.extension.compose)

                implementation(deps.toolbox.ui)

                //implementation(project(":demo:shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.michaelflisar.composepreferences.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "ComposePreferences JVM Demo"
            packageVersion = "1.0.0"
        }
    }
}