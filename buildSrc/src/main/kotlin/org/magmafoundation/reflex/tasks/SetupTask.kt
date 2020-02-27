package org.magmafoundation.reflex.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.magmafoundation.reflex.utils.IOUtils.downloadFile
import java.io.File

open class SetupTask : DefaultTask() {

    val mcBuildDir = File(project.rootProject.buildDir, "minecraft");
    val clientJar = File(mcBuildDir, "client.jar");
    val serverJar = File(mcBuildDir, "server.jar");

    @TaskAction
    fun setup() {
        println("BetaGradle setting up workspace...")


        if(!mcBuildDir.exists())
            mcBuildDir.mkdirs()

        downloadFile(clientJar, "http://s3.amazonaws.com/Minecraft.Download/versions/b1.7.3/b1.7.3.jar")
        downloadFile(serverJar, "https://repo.kingdev.nl/repository/files/1.7.3-beta-server.jar")
       (project.tasks.getByName("deobfuscate") as DeobfuscateTask).deobfuscate()

    }



}