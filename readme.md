# GUI Vista Core (guivista-core)

A Kotlin Native library that provides core functionality, which is based on the 
[GLib](https://developer.gnome.org/glib/) library. This library depends on Kotlin Native which is currently in beta, 
and doesn't provide any backwards compatibility guarantees!


## Publish Library

Currently GUI Vista Core isn't available in Maven Central, JCenter, or any other remote Maven repository. Do the 
following to publish the library:

1. Clone this repository
2. Change working directory to where the repository has been cloned to
3. Publish the library locally via Gradle, eg `./gradlew publishLinuxX64PublicationToMavenLocal`


## Setup Gradle Build File

In order to use the library with Gradle do the following:

1. Open/create a Kotlin Native project which targets **linuxX64**
2. Open the project's **build.gradle.kts** file
3. Insert `mavenLocal()` into the **repositories** block
4. Add the library dependency: `implementation("org.guivista:guivista-core:0.1-SNAPSHOT")`

The build file should look similar to the following:
```kotlin
// ...
repositories {
    mavenLocal()
}

kotlin {
    // ...
    linuxX64() {
        // ...
        compilations.getByName("main") {
            dependencies {
                val guiVistaVer = "0.1-SNAPSHOT"
                implementation("org.guivista:guivista-core-linuxx64:$guiVistaVer")
            }
        }
    }
}
```
