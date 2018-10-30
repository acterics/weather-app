package com.acterics.weatherapp

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*
import java.io.File

open class PackTask: Exec() {

    init {
        group = "deploy"
        description = "Packing sources"
    }

//    private val outputFolder by lazy {
//        File("${project.rootDir.absolutePath}/dist")
//    }
//
//    @OutputDirectory
//    fun getOutputDirectory() = outputFolder

    override fun exec() {
        val scriptName = "pack.sh"
        val scriptPath = "${project.rootDir.absolutePath}/buildSrc/src/main/bash/$scriptName"
        
        println("Packing sources to dist directory")
        commandLine = listOf(scriptPath)
        super.exec()
    }
}

fun Project.withPackTask() {
    val packTask = tasks.create("packSources", PackTask::class.java)
}