package dev.daniel366cobra.vs_marinepropulsion.fabric;

import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import dev.daniel366cobra.vs_marinepropulsion.fabric.items.EngineOrderTelegraphBlockItemFabric;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.*;
import net.minecraft.world.level.block.Block;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class VS_MarinePropulsionExpectPlatformImpl {

	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}

	public static BakedModel loadBakedModel(ResourceLocation resourceLocation) {
		return Minecraft.getInstance()
				.getModelManager()
				.getModel(resourceLocation);
	}

	public static NonNullBiFunction<Block, Properties, ? extends EngineOrderTelegraphBlockItem> getSpecificEngineOrderTelegraphBlockItemConstructor() {
		return EngineOrderTelegraphBlockItemFabric::new;
	}
}
