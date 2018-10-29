import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent
import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent.Companion.EXECUTABLE

repositories {
    jcenter()
}

plugins {
    id("org.jetbrains.kotlin.platform.native") version Versions.kotlinNative
}

val RASPBERRY_IP = this.property("com.acterics.raspberry_pi_ip").toString()


group = "com.acterics"
version = "0.1"



sourceSets.getByName("main").component {
    (this as KotlinNativeMainComponent).outputKinds.add(EXECUTABLE)
    targets = listOf("linux_x64", "linux_arm32_hfp")

    dependencies {
        cinterop("libcurl-interop") {
            defFile("src/main/c_interop/libcurl.def")
        }
        cinterop("cjson-interop") {
            defFile("src/main/c_interop/cjson.def")
        }

    }
}



withDeployTask(RASPBERRY_IP)


