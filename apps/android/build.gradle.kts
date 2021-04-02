plugins {
    id(Plugins.androidApp)
    id(Plugins.kotlinAndroid)
    id("kotlin-kapt")
}

android {

    compileSdkVersion(Versions.Android.compileSdk)
    buildToolsVersion(Versions.Android.buildTools)

    defaultConfig {

        applicationId = AppInfo.Android.id
        versionName = AppInfo.Android.versionName
        versionCode = AppInfo.Android.versionCode

        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)

        testInstrumentationRunner = AppInfo.Android.testInstRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = AppInfo.Android.useDataBinding
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*.kotlin_module")
    }

    android.sourceSets.all {
        java.srcDir("src/$name/kotlin")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    listOf(
        Dependencies.kotlinStdLib,
        Dependencies.androidCoreKtx
    ).forEach {
        implementation("$it")
    }

    Dependencies.androidUi.forEach {
        implementation("$it")
    }
/*
    Dependencies.Libs.tests.forEach {
        testImplementation("$it")
    }

    Dependencies.Libs.instrumentationTests.forEach {
        androidTestImplementation("$it")
    }*/

    // Android apps can only access to presentation layer and DI module
    implementation(project("${Modules.presentation}"))
    implementation(project("${Modules.di}"))
    implementation(project("${Modules.domain}"))
}