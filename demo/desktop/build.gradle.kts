import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {

    jvm {
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {

                implementation(compose.desktop.currentOs)

                implementation(libs.compose.material3)
                implementation(libs.compose.material.icons.core)
                implementation(libs.compose.material.icons.extended)

                implementation(kotlinx.datetime)

                // Libraries
                implementation(project(":ComposePreferences:Core"))
                implementation(project(":ComposePreferences:Modules:Screen:Bool"))
                implementation(project(":ComposePreferences:Modules:Screen:Button"))
                implementation(project(":ComposePreferences:Modules:Screen:Input"))
                implementation(project(":ComposePreferences:Modules:Screen:Color"))
                implementation(project(":ComposePreferences:Modules:Screen:Date"))
                implementation(project(":ComposePreferences:Modules:Screen:Time"))
                implementation(project(":ComposePreferences:Modules:Screen:List"))
                implementation(project(":ComposePreferences:Modules:Screen:Number"))
                implementation(project(":ComposePreferences:Modules:KotPreferences"))

                // KotPreferences
                implementation(deps.kotpreferences.core)
                implementation(deps.kotpreferences.storage.datastore)
                implementation(deps.kotpreferences.extension.compose)

                implementation(deps.toolbox.core)
                implementation(deps.toolbox.ui)
                //implementation(libs.toolbox.windows.app)
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