package dev.daniel366cobra.vs_marinepropulsion.forge;

import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionPartialModels;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VS_MarinePropulsionMod.MOD_ID)
public class VS_MarinePropulsionModForge {

    public VS_MarinePropulsionModForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        VS_MarinePropulsionMod.REGISTRATE.registerEventListeners(eventBus);
        VS_MarinePropulsionPartialModels.init();
        VS_MarinePropulsionCreativeTabForge.register(eventBus);

        VS_MarinePropulsionMod.init();
    }

}
