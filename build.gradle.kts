import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent
import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent.Companion.EXECUTABLE

repositories {
    jcenter()
}

plugins {
    id("org.jetbrains.kotlin.platform.native") version Versions.kotlinNative
}

val RASPBERRY_IP = "192.168.0.106"

group = "com.acterics"
version = "0.1"

compileOpenSourceC("cjson", "https://github.com/DaveGamble/cJSON/archive/v1.7.8.tar.gz")

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}")
}

sourceSets.getByName("main").component {
    (this as KotlinNativeMainComponent).outputKinds.add(EXECUTABLE)
//    targets = listOf("linux_x64", "linux_arm32_hfp")
    targets = listOf("linux_x64")
    dependencies {
        cinterop("libcurl-interop") {
            defFile("src/main/c_interop/libcurl.def")

            target("linux_x64") {
                includeDirs {
                    headerFilterOnly(listOf("/usr/include", "/usr/include/x86_64-linux-gnu"))
                }
            }
            target("linux_arm32_hfp") {
                includeDirs {
                    headerFilterOnly(listOf("/src/include/"))
                }
            }
            target("macos_x64") {
                includeDirs {
                    headerFilterOnly(listOf("/opt/local/include", "/usr/local/include"))
                }
            }
        }
        cinterop("cjson-interop") {
            defFile("src/main/c_interop/cjson.def")
        }
    }
}



withDeployTask(RASPBERRY_IP)
withExecuteTask()




