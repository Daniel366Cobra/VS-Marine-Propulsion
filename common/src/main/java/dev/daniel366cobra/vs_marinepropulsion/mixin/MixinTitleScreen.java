package dev.daniel366cobra.vs_marinepropulsion.mixin;

import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        VS_MarinePropulsionMod.LOGGER.info("Hello from {}!", VS_MarinePropulsionMod.class.getName());
    }
}
