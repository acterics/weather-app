import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*

open class DeployTask: Exec() {

    init {
        group = "deploy"
        description = "Deploy build binaries to raspberry pi"
    }

    lateinit var ipAddress: String

    override fun exec() {
        val buildDirPath = "${project.buildDir}/exe/main/debug/executable/linux_arm32_hfp/"
        val sshAddress = "pi@${ipAddress}"
        val raspberryDestPath = "/home/pi/Projects/kotlin-native/hello-world/"
        println("Deploying build binaries to raspberry pi")
        println("Raspberry PI ip address: $ipAddress")

        println("Raspberry PI build dir: $buildDirPath")
        println("SSH address: $sshAddress")
        println("Raspberry destination path: $raspberryDestPath")

        commandLine = listOf("bash", "-c", "rsync -rvz $buildDirPath $sshAddress:$raspberryDestPath")
        super.exec()
    }

}


fun Project.withDeployTask(ip: String) {
    val deployProvider = tasks.create("deployToRaspberryPi", DeployTask::class.java) {
        ipAddress = ip
    }
    deployProvider.dependsOn(tasks.getByName("build"))
}