import org.gradle.api.Project
import org.gradle.api.tasks.Exec

open class ExecuteTask: Exec() {

    init {
        group = "run"
        description = "Execute weather app"
    }

    override fun exec() {
        println("Start executing weather-app.kexe")
        val path = "${project.buildDir}/exe/main/debug"
        val name = "weather-app.kexe"
        commandLine = listOf("bash", "-c", "$path/$name")
        super.exec()
    }
}


fun Project.withExecuteTask() {
    val taskProvider = tasks.create("run", ExecuteTask::class.java)
//    taskProvider.get().dependsOn(tasks.getByName("build"))
}