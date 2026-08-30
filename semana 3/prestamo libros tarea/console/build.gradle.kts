plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.barzola.prestamodelibros.PrestamoKt")
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
        attributes["Main-Class"] = "com.barzola.prestamodelibros.PrestamoKt"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    val deps = configurations.runtimeClasspath
    from(deps.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
