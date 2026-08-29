plugins {
    kotlin("jvm") version "2.0.20"
    application
}

application {
    mainClass.set("com.barzola.lab02carritokotlin.CarritoKt")
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.register<Jar>("fatJar") {
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "com.barzola.lab02carritokotlin.CarritoKt"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    val deps = configurations.runtimeClasspath
    from(deps.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
