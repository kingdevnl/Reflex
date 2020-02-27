package org.magmafoundation.reflex.tasks

import net.md_5.specialsource.Jar
import net.md_5.specialsource.JarMapping
import net.md_5.specialsource.JarRemapper
import net.md_5.specialsource.provider.JointProvider
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.magmafoundation.reflex.utils.AccessModder
import java.io.File


open class ObfuscateTask : DefaultTask() {
    val mcBuildDir = File(project.rootProject.buildDir, "minecraft")
    val clientJar = File(mcBuildDir, "client.jar")
    val serverJar = File(mcBuildDir, "server.jar")

    @TaskAction
    fun obfuscate() {

        val allArtifacts = project.configurations.getByName("archives").allArtifacts
        allArtifacts.forEach {

            if (it.name.toLowerCase().contains("client")) {
                mapClient(it.file)
            }
            if(it.name.toLowerCase().contains("server")) {
                mapServer(it.file)
            }


        }

    }


    private fun mapClient(input: File) {
        val jarMapping = JarMapping()
        println("Mapping Client Jar (might take a long time) ${input.absolutePath} ")
        jarMapping.loadMappings(
            File(project.rootProject.projectDir, "mappings/client.srg").absolutePath,
            true,
            false,
            null,
            null
        )
        val inheritanceProviders = JointProvider()
        jarMapping.setFallbackInheritanceProvider(inheritanceProviders)

        val jarRemapper = JarRemapper(jarMapping)
        val jar = Jar.init(input)

        val output = File(input.parent, "out.jar")

        jarRemapper.remapJar(jar, output)

        jar.close()
        output.copyTo(input, true);
        output.delete()


    }
    private fun mapServer(input: File) {
        val jarMapping = JarMapping()
        println("Mapping Server Jar (might take a long time) ${input.absolutePath} ")
        jarMapping.loadMappings(
            File(project.rootProject.projectDir, "mappings/server.srg").absolutePath,
            true,
            false,
            null,
            null
        )
        val inheritanceProviders = JointProvider()
        jarMapping.setFallbackInheritanceProvider(inheritanceProviders)

        val jarRemapper = JarRemapper(jarMapping)
        val jar = Jar.init(input)

        val output = File(input.parent, "out.jar")

        jarRemapper.remapJar(jar, output)

        jar.close()
        output.copyTo(input, true);
        output.delete()


    }


}



