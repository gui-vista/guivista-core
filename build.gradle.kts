group = "org.guivista"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.3.61"
    `maven-publish`
}

repositories {
    jcenter()
    mavenCentral()
}

kotlin {
    linuxX64("linuxX64") {
        compilations.getByName("main") {
            cinterops.create("glib2_x64") {
                includeDirs("/usr/include/glib-2.0")
            }
        }
    }
    linuxArm32Hfp("linuxArm32") {
        compilations.getByName("main") {
            cinterops.create("glib2_arm32") {
                includeDirs("/mnt/pi_image/usr/include/glib-2.0")
            }
        }
    }
    sourceSets {
        @Suppress("UNUSED_VARIABLE") val linuxX64Main by getting {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
        @Suppress("UNUSED_VARIABLE") val linuxArm32Main by getting {
            dependsOn(linuxX64Main)
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}
