package dev.daniel366cobra.vs_marinepropulsion;

import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.daniel366cobra.vs_marinepropulsion.blocks.controls.engine_order_telegraph.EngineOrderTelegraphBlockEntity;
import dev.daniel366cobra.vs_marinepropulsion.blocks.controls.engine_order_telegraph.EngineOrderTelegraphBlockEntityRenderer;
import dev.daniel366cobra.vs_marinepropulsion.blocks.controls.engine_order_telegraph.EngineOrderTelegraphInstance;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller.LargeShipPropellerInstance;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller.ShipPropellerBlockEntity;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller.ShipPropellerBlockEntityRenderer;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator.VariatorBlockEntity;

public class VS_MarinePropulsionBlockEntities {

    private static final CreateRegistrate REGISTRATE = VS_MarinePropulsionMod.getRegistrate();

    public static final BlockEntityEntry<ShipPropellerBlockEntity> LARGE_SHIP_PROPELLER_BLOCK_ENTITY = REGISTRATE
            .blockEntity("large_ship_propeller_entity", ShipPropellerBlockEntity::new)
            .instance(() -> LargeShipPropellerInstance::new, false)
            .validBlocks(VS_MarinePropulsionBlocks.LARGE_SHIP_PROPELLER)
            .renderer(() -> ShipPropellerBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<VariatorBlockEntity> VARIATOR_BLOCK_ENTITY = REGISTRATE
            .blockEntity("variator_entity", VariatorBlockEntity::new)
            .instance(() -> SplitShaftInstance::new, false)
            .validBlocks(VS_MarinePropulsionBlocks.VARIATOR)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<EngineOrderTelegraphBlockEntity> ENGINE_ORDER_TELEGRAPH_BLOCK_ENTITY = REGISTRATE
            .blockEntity("engine_order_telegraph_entity", EngineOrderTelegraphBlockEntity::new)
            .instance(() -> EngineOrderTelegraphInstance::new, false)
            .validBlocks(VS_MarinePropulsionBlocks.ENGINE_ORDER_TELEGRAPH)
            .renderer(() -> EngineOrderTelegraphBlockEntityRenderer::new)
            .register();

    public static void register() {
        VS_MarinePropulsionMod.LOGGER.info("Registering block entities for " + VS_MarinePropulsionMod.NAME);
    }
}
