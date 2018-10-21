import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import java.io.File

open class AddCDependencyTask: Exec() {
    companion object {
        const val SCRIPT_NAME = "add_c_dependency.sh"
    }
    init {
        group = "build"
        description = "Downloade and compile C dependency"
    }


    val scriptPath by lazy { "${project.rootDir.absolutePath}/buildSrc/src/main/bash/$SCRIPT_NAME"}
    val taskOutputDirectoryPath by lazy { "${project.buildDir}/c-dependencies" }
    val workingDir by lazy { "$taskOutputDirectoryPath/$dependencyName" }
    val outputDirectoryFile by lazy { File(workingDir) }

    @Input lateinit var dependencyName: String
    @Input lateinit var sourceAddress: String

    @OutputDirectory
    fun getOutputDirectory() = outputDirectoryFile

    /**
     * Uses add_c_dependency.sh script
     * @param
     * dependencyName
     * downloadLink
     * workingDir
     */
    override fun exec() {
        commandLine = listOf(scriptPath, dependencyName, sourceAddress, workingDir)
        super.exec()
    }

}


fun Project.compileOpenSourceC(name: String, path: String) {
    val dependencyTaskName = "compileOpenSource${name.capitalize()}"
    val task = tasks.create(dependencyTaskName, AddCDependencyTask::class.java) {
        dependencyName = name
        sourceAddress = path
    }
    tasks.getByName("build").dependsOn(task)
}