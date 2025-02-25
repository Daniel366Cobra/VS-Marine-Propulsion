package dev.daniel366cobra.vs_marinepropulsion;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import dev.daniel366cobra.vs_marinepropulsion.blocks.controls.engine_order_telegraph.EngineOrderTelegraphBlock;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller.LargeShipPropellerBlock;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator.VariatorBlock;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class VS_MarinePropulsionBlocks {

	private static final CreateRegistrate REGISTRATE = VS_MarinePropulsionMod.getRegistrate();

	public static final BlockEntry<LargeShipPropellerBlock> LARGE_SHIP_PROPELLER = REGISTRATE
			.block("large_ship_propeller", LargeShipPropellerBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p
					.noOcclusion()
					.mapColor(MapColor.DEEPSLATE)
					.strength(3.5f))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(BlockStressDefaults.setImpact(4.0f))
			.transform(axeOrPickaxe())
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<VariatorBlock> VARIATOR = REGISTRATE
			.block("variator", VariatorBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(BlockStressDefaults.setNoImpact())
			.transform(axeOrPickaxe())
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
			.item()
			.transform(customItemModel())
			.register();

	private static final NonNullBiFunction<Block, Item.Properties, ? extends EngineOrderTelegraphBlockItem> specificEngineOrderTelegraphBlockItemConstructor =
			VS_MarinePropulsionExpectPlatform.getSpecificEngineOrderTelegraphBlockItemConstructor();

	public static final BlockEntry<EngineOrderTelegraphBlock> ENGINE_ORDER_TELEGRAPH = REGISTRATE
			.block("engine_order_telegraph", EngineOrderTelegraphBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p
					.noOcclusion()
					.mapColor(MapColor.DEEPSLATE))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(axeOrPickaxe())
			.item(specificEngineOrderTelegraphBlockItemConstructor)
			.transform(customItemModel("_", "engine_order_telegraph"))
			.register();

	public static void register() {
		VS_MarinePropulsionMod.LOGGER.info("Registering blocks for " + VS_MarinePropulsionMod.NAME);
	}
}
