package dev.daniel366cobra.vs_marinepropulsion.forge.client;

import dev.daniel366cobra.vs_marinepropulsion.forge.items.EngineOrderTelegraphBlockItemForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class VS_MarinePropulsionModClientEventsForge {
        @SubscribeEvent
        public static void onTick(TickEvent.ClientTickEvent event) {
                EngineOrderTelegraphBlockItemForge.clientTick();
        }
}
