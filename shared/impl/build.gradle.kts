plugins {
    id(Plugins.kotlinJvm)
    Plugins.javaLibrary
}

dependencies {

    implementation(project(Modules.data))
}