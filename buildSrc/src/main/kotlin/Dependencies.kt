object Dependencies {

    const val stdLib            = "stdlib-jdk8"
    const val tornadoFx         = "no.tornado:tornadofx:${Versions.tornadoFX}"
    const val coroutinesCore    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesFx      = "org.jetbrains.kotlinx:kotlinx-coroutines-javafx:${Versions.coroutines}"

    const val junit             = "test-junit"

    const val kotlinStdLib      = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val androidCoreKtx    = "androidx.core:core-ktx:1.3.2"

    val androidUi = listOf(
        "androidx.appcompat:appcompat:1.2.0",
        "androidx.constraintlayout:constraintlayout:2.0.4",
        "com.google.android:flexbox:2.0.1",
        "androidx.navigation:navigation-fragment:2.3.2",
        "androidx.navigation:navigation-ui:2.3.2",
        "androidx.navigation:navigation-fragment-ktx:2.3.2",
        "androidx.navigation:navigation-ui-ktx:2.3.2",
        "androidx.fragment:fragment-testing:1.2.5",
        "com.squareup.picasso:picasso:2.8",
        "jp.wasabeef:picasso-transformations:2.2.1"
    )

   /* val tests = listOf(
        "junit:junit:4.+",
        "androidx.test.ext:junit:${Versions.Libs.junitXExt}",
        "android.arch.core:core-testing:${Versions.Libs.androidArchCoreT}",
        "org.koin:koin-test:${Versions.Libs.koin}",
        "io.mockk:mockk:${Versions.Libs.mockkVersion}"
    )

    val instrumentationTests = listOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Libs.coroutines}",
        "androidx.test.ext:junit:1.1.2",
        "androidx.test.espresso:espresso-core:3.3.0",
        "androidx.test.ext:junit:${Versions.Libs.junitXExt}",
        "androidx.test:rules:${Versions.Libs.testxRules}",
        "android.arch.core:core-testing:${Versions.Libs.androidArchCoreT}",
        "org.koin:koin-test:${Versions.Libs.koin}",
        "androidx.test.espresso:espresso-contrib:${Versions.Libs.espressoContrib}"
    )*/
}