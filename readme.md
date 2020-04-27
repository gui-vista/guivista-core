# GUI Vista Core (guivista-core)

A Kotlin Native library that provides core functionality, which is based on the 
[GLib](https://developer.gnome.org/glib/) library. This library depends on Kotlin Native which is currently in beta, 
and doesn't provide any backwards compatibility guarantees! Currently, GUI Vista Core isn't available in Maven Central 
or JCenter, but is available in a remote GitLab Maven repository.


## Setup Gradle Build File

In order to use the library with Gradle (version 5.4 or higher) do the following:

1. Open/create a Kotlin Native project which targets **linuxX64** or **linuxArm32Hfp**
2. Open the project's **build.gradle.kts** file
3. Insert the following into the **repositories** block:
```kotlin
maven {
    val projectId = "16245519"
    url = uri("https://gitlab.com/api/v4/projects/$projectId/packages/maven")
}
```
4. Add the library dependency: `implementation("org.guivista:guivista-core-$target:$guiVistaVer")`

The build file should look similar to the following:
```kotlin
// ...
repositories {
    maven {
        val projectId = "16245519"
        url = uri("https://gitlab.com/api/v4/projects/$projectId/packages/maven")
    }
}

kotlin {
    // ...
    // Replace with linuxArm32Hfp if that target is used in the project.
    linuxX64 {
        // ...
        compilations.getByName("main") {
            dependencies {
                // For a ARM v7 based SBC use the linuxarm32 target.
                val target = "linuxx64"
                val guiVistaVer = "0.1"
                implementation("org.guivista:guivista-core-$target:$guiVistaVer")
            }
        }
    }
}
```
