package dev.daniel366cobra.vs_marinepropulsion.fabric.client;

import com.simibubi.create.foundation.events.ClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VS_MarinePropulsionModFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEvents.register();
    }
}
