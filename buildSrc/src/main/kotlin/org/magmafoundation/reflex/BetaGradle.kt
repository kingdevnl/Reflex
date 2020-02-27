package org.magmafoundation.reflex.BetaGradle



import org.gradle.api.Plugin
import org.gradle.api.Project
import org.magmafoundation.reflex.tasks.DeobfuscateTask
import org.magmafoundation.reflex.tasks.ObfuscateTask
import org.magmafoundation.reflex.tasks.SetupTask

open class BetaGradle: Plugin<Project> {
    override fun apply(project: Project) {

        project.tasks.create("setup", SetupTask::class.java) {
            it.group = "betagradle"
        }

        project.tasks.create("deobfuscate", DeobfuscateTask::class.java) {
            it.group = "betagradle"
        }
        project.tasks.create("obfuscate", ObfuscateTask::class.java) {
            it.group = "betagradle"

            project.tasks.getByName("build").finalizedBy(it)
        }







    }

}