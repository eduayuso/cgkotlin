plugins {
    id(Plugins.kotlinJvm)
    Plugins.javaLibrary
}

dependencies {

    implementation(project("${Modules.impl}"))
    implementation(project("${Modules.data}"))
    implementation(project("${Modules.presentation}"))
    implementation(project("${Modules.domain}"))
}