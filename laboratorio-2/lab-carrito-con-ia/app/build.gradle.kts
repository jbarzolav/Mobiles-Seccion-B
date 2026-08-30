plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.barzola.lab02carritokotlin"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.barzola.lab02carritokotlin"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    tasks.register<JavaExec>("runCarrito") {
        dependsOn("compileDebugKotlin")
        classpath =
            files(layout.buildDirectory.dir("intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes")) +
                    configurations.getByName("debugRuntimeClasspath")
        mainClass.set("com.barzola.lab02carritokotlin.CarritoKt")
        standardInput = System.`in`
    }
}