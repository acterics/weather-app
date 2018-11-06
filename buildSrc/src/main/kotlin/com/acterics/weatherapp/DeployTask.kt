package com.acterics.weatherapp

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*

open class DeployTask: Exec() {

    init {
        group = "deploy"
        description = "Deploy sources to raspberry pi"
    }

    lateinit var ipAddress: String

    override fun exec() {
        val buildDirPath = "${project.rootDir.absolutePath}/bin/"
        val sshAddress = "pi@${ipAddress}"
        val raspberryDestPath = "/home/pi/Projects/kotlin-native/${project.name}/bin"
        println("Deploying sources to raspberry pi")
        println("Raspberry PI ip address: $ipAddress")

        println("Raspberry PI build dir: $buildDirPath")
        println("SSH address: $sshAddress")
        println("Raspberry destination path: $raspberryDestPath")

        commandLine = listOf("bash", "-c", "rsync -rvz --ignore-times $buildDirPath $sshAddress:$raspberryDestPath")
        super.exec()
    }

}


fun Project.withDeployTask(ip: String) {
    val deployTask = tasks.create("deployToRaspberryPi", DeployTask::class.java) {
        ipAddress = ip
    }
    deployTask.dependsOn(tasks.getByName("packSources"))
}