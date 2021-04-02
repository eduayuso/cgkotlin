plugins {
    kotlin(Plugins.jvm)
    application
}
group = AppInfo.Desktop.group
version = AppInfo.Desktop.version

repositories {
    mavenCentral()
}

application {
    mainClassName = AppInfo.Desktop.mainClass
}

dependencies {

    implementation(kotlin(Dependencies.stdLib))
    implementation(Dependencies.tornadoFx)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesFx)
    testImplementation(kotlin(Dependencies.junit))

    implementation(project("${Modules.di}"))
    implementation(project("${Modules.presentation}"))
    implementation(project("${Modules.domain}"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = Versions.java
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = Versions.java
    }
}