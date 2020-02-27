package org.magmafoundation.reflex.client

import net.minecraft.client.MinecraftClient
import org.spongepowered.asm.mixin.MixinEnvironment


fun main() {

    val mc = MinecraftClient.INSTANCE
    mc.theWorld.setBlock(1, 2, 3, 2)


}