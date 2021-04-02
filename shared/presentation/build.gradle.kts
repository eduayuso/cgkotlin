plugins {
    id(Plugins.kotlinJvm)
    Plugins.javaLibrary
}

dependencies {

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesFx)
    implementation(project(Modules.data))
}