package org.magmafoundation.reflex.client.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.UnexpectedThrowable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class, remap = false)
public class MixinMinecraftClient {

    @Shadow public static MinecraftClient INSTANCE;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void starGame(CallbackInfo callbackInfo) {

    }
}
