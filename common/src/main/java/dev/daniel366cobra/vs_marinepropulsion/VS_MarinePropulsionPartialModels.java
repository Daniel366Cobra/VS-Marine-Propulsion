package dev.daniel366cobra.vs_marinepropulsion;

import com.jozufozu.flywheel.core.PartialModel;

public final class VS_MarinePropulsionPartialModels {

    public static final PartialModel LARGE_SHIP_PROPELLER = block("large_ship_propeller/blades");
    public static final PartialModel ENGINE_ORDER_TELEGRAPH_LEVER = block("engine_order_telegraph/lever");

    private static PartialModel block(String path) {
        return new PartialModel(VS_MarinePropulsionMod.resourceLocationFromPath("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(VS_MarinePropulsionMod.resourceLocationFromPath("entity/" + path));
    }

    public static void init() {
        VS_MarinePropulsionMod.LOGGER.info("Initializing Partial Models");
    }

}
