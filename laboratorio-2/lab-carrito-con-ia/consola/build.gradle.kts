plugins {
    id("org.jetbrains.kotlin.jvm")
    application
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("com.barzola.lab02carritokotlin.CarritoKt")
}