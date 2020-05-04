plugins {
    kotlin("multiplatform") version "1.4-M1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
}

val mingw64Path = File(System.getenv("MINGW64_DIR") ?: "C:/msys64/mingw64")

kotlin {

    mingwX64 {
        binaries {
            executable {
                entryPoint = "sample.gtk.main"
                linkerOpts("-L${mingw64Path.resolve("lib")}")
                runTask?.environment("PATH" to mingw64Path.resolve("bin"))
            }
        }
        val main by compilations.getting {
            cinterops {
                val gtk3 by creating {
                    listOf(
                        "include/atk-1.0",
                        "include/gdk-pixbuf-2.0",
                        "include/cairo",
                        "include/pango-1.0",
                        "include/gtk-3.0",
                        "include/glib-2.0",
                        "lib/glib-2.0/include"
                    ).forEach {
                        includeDirs(mingw64Path.resolve(it))
                    }
                }
            }
        }

    }

}