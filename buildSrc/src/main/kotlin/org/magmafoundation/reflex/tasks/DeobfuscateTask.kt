package org.magmafoundation.reflex.tasks

import net.md_5.specialsource.Jar
import net.md_5.specialsource.JarMapping
import net.md_5.specialsource.JarRemapper
import net.md_5.specialsource.provider.JointProvider
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.magmafoundation.reflex.utils.AccessModder
import java.io.File


open class DeobfuscateTask : DefaultTask() {
    val mcBuildDir = File(project.rootProject.buildDir, "minecraft")
    val clientJar = File(mcBuildDir, "client.jar")
    val serverJar = File(mcBuildDir, "server.jar")

    @TaskAction
    fun deobfuscate() {
        mapClient()
        mapServer()

    }


    private fun mapClient() {
        val jarMapping = JarMapping()
        println("Mapping Client Jar (might take a long time)")
        jarMapping.loadMappings(File(project.rootProject.projectDir,"mappings/client.srg").absolutePath, false, false, null, null)
        val inheritanceProviders = JointProvider()
        jarMapping.setFallbackInheritanceProvider(inheritanceProviders)

        val jarRemapper = JarRemapper(jarMapping)
        val jar = Jar.init(clientJar)
        jarRemapper.remapJar(jar, File(mcBuildDir, "client_mapped.jar"))
        AccessModder.transformJar(File(mcBuildDir, "client_mapped.jar"),File(mcBuildDir, "client_mapped.jar"));

    }

    private fun mapServer() {
        val jarMapping = JarMapping()
        println("Mapping Server Jar (might take a long time)")
        jarMapping.loadMappings(File(project.rootProject.projectDir,"mappings/server.srg").absolutePath, false, false, null, null)
        val inheritanceProviders = JointProvider()
        jarMapping.setFallbackInheritanceProvider(inheritanceProviders)

        val jarRemapper = JarRemapper(jarMapping)
        val jar = Jar.init(serverJar)
        jarRemapper.remapJar(jar, File(mcBuildDir, "server_mapped.jar"))
        AccessModder.transformJar(File(mcBuildDir, "server_mapped.jar"),File(mcBuildDir, "server_mapped.jar"));

    }
}