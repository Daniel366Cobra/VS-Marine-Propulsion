package dev.daniel366cobra.vs_marinepropulsion.forge;

import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import dev.daniel366cobra.vs_marinepropulsion.forge.items.EngineOrderTelegraphBlockItemForge;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VS_MarinePropulsionExpectPlatformImpl {

	public static String platformName() {
		return "Forge";
	}

	public static BakedModel loadBakedModel(ResourceLocation resourceLocation) {
		ModelBakery mb = Minecraft.getInstance().getModelManager().getModelBakery();
		return mb.getBakedTopLevelModels()
				.getOrDefault(
						resourceLocation,
						null
				);
	}

	public static NonNullBiFunction<Block, Item.Properties, ? extends EngineOrderTelegraphBlockItem> getSpecificEngineOrderTelegraphBlockItemConstructor() {
		return EngineOrderTelegraphBlockItemForge::new;
	}
}
