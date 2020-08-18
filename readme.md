# GUI Vista Core (guivista-core)

A Kotlin Native library that provides core functionality, which is based on the 
[GLib](https://developer.gnome.org/glib/) library. This library depends on Kotlin Native (requires Kotlin 1.4.0) which 
is currently in beta, and doesn't provide any backwards compatibility guarantees! Currently, GUI Vista Core isn't 
available in Maven Central or JCenter, but is available in a remote Bintray Maven repository.


## Setup Gradle Build File

In order to use the library with Gradle (version 6.0 or higher) do the following:

1. Open/create a Kotlin Native project which targets **linuxX64** or **linuxArm32Hfp**
2. Open the project's **build.gradle.kts** file
3. Insert the following into the **repositories** block:
```kotlin
maven {
    url = uri("https://dl.bintray.com/guivista/public")
}
```
4. Create a library definition file called **glib2.def** which contains the following:
```
linkerOpts = -lglib-2.0 -lgobject-2.0
linkerOpts.linux_x64 = -L/usr/lib/x86_64-linux-gnu
linkerOpts.linux_arm32_hfp = -L/mnt/pi_image/usr/lib/arm-linux-gnueabihf
```
5. Add the glib2 library dependency: `cinterops.create("glib2")`
6. Add the GUI Vista Core library dependency: `implementation("org.guivista:guivista-core:$guiVistaVer")`

The build file should look similar to the following:
```kotlin
// ...
repositories {
    maven {
        url = uri("https://dl.bintray.com/guivista/public")
    }
}

kotlin {
    // ...
    linuxX64 {
        // ...
        compilations.getByName("main") {
            dependencies {
                val guiVistaVer = "0.2.0"
                cinterops.create("glib2")
                implementation("org.guivista:guivista-core:$guiVistaVer")
            }
        }
    }
}
```
