package dev.daniel366cobra.vs_marinepropulsion.fabric.client;

import com.simibubi.create.foundation.events.ClientEvents;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

import static com.jozufozu.flywheel.backend.Backend.isGameActive;

public class ClientEventsFabric {
    public static void onTick(Minecraft client) {
        if (!isGameActive())
            return;
        EngineOrderTelegraphBlockItem.clientTick();
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientEvents::onTick);
    }
}
