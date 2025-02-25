package dev.daniel366cobra.vs_marinepropulsion.fabric;

import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

public class VS_MarinePropulsionModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VS_MarinePropulsionMod.init();
        VS_MarinePropulsionMod.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), VS_MarinePropulsionMod.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        VS_MarinePropulsionMod.REGISTRATE.register();
    }
}
