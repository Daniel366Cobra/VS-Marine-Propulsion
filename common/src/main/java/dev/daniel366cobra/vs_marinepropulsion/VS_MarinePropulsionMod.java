package dev.daniel366cobra.vs_marinepropulsion;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Temporary borrows:
 * Ship propeller model & texture - VS Tournament
 * Stepped Lever model & texture - Design N Decor
 * Parts of the Freewheel clutch texture - Create Connected
 */
public class VS_MarinePropulsionMod {
    public static final String MOD_ID = "vs_marinepropulsion";
    public static final String NAME = "VS Marine Propulsion";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(VS_MarinePropulsionMod.MOD_ID);

    static {
        REGISTRATE.setTooltipModifierFactory(
                item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public static void init() {
        LOGGER.info("{} initializing! Create version: {} on platform: {}", NAME, Create.VERSION, VS_MarinePropulsionExpectPlatform.platformName());
        VS_MarinePropulsionBlocks.register();
        VS_MarinePropulsionBlockEntities.register();

    }

    public static ResourceLocation resourceLocationFromPath(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static CreateRegistrate getRegistrate() {
        return REGISTRATE;
    }
}
