import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

fun kapt(dependency: Provider<MinimalExternalModuleDependency>) {
    configurations.getByName("kapt").dependencies.add(dependency.get())
}

fun ksp(dependency: Provider<MinimalExternalModuleDependency>) {
    configurations.getByName("ksp").dependencies.add(dependency.get())
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinx.atomicfu)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.auto.service)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.recyclerview)
            implementation(libs.androidx.coordinatorlayout)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.runtime.livedata)
            implementation(libs.androidx.recyclerview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.coil.network.okhttp)
            implementation(libs.ktor.client.android)
//            implementation(libs.androidx.constraintlayout)
//            implementation(libs.androidx.constraintlayout.compose)
//            kapt(libs.auto.service)
//            ksp(libs.zacsweers.autoservice.ksp)
        }
        commonMain.dependencies {
            implementation(kotlin("reflect"))
            implementation(projects.shared)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.datetime)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.constraintlayout.compose.multiplatform)
            implementation(libs.kotlinx.atomicfu)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.0-Beta3"))
            implementation("io.insert-koin:koin-core:4.1.0-Beta3")
            api("io.insert-koin:koin-annotations:2.0.0-Beta3")
            implementation("io.insert-koin:koin-compose:4.1.0-Beta3")
            implementation("io.insert-koin:koin-compose-viewmodel:4.1.0-Beta3")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.ktor.client.java)
        }
    }
}

android {
    namespace = "compose.project.demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "compose.project.demo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    add("kspCommonMainMetadata", "io.insert-koin:koin-ksp-compiler:2.0.0-Beta3")
    add("kspAndroid", "io.insert-koin:koin-ksp-compiler:2.0.0-Beta3")
}

compose.desktop {
    application {
        mainClass = "compose.project.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "compose.project.demo"
            packageVersion = "1.0.0"
        }
    }
}

project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if(name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
