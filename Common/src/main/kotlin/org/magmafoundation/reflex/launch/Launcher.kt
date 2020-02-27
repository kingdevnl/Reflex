package org.magmafoundation.reflex.launch

import net.minecraft.launchwrapper.Launch

object Launcher {

    public fun launch(tweaker: String, args: Array<String>) {

       Launch.main(arrayOf("--tweakClass", tweaker).plus(args))
    }
}