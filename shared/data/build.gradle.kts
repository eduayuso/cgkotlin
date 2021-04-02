plugins {
    id(Plugins.kotlinJvm)
    Plugins.javaLibrary
}

dependencies {

    // Data layer only depends on domain layer
    api(project("${Modules.domain}"))
}