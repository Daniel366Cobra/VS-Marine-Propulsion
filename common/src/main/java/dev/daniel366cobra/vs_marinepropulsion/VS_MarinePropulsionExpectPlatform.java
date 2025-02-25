package dev.daniel366cobra.vs_marinepropulsion;

import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VS_MarinePropulsionExpectPlatform {
    /**
     * an example of {@link ExpectPlatform}.
     * <p>
     * This must be a <b>public static</b> method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * Expect: net.examplemod.ExampleExpectPlatform#platformName()
     * Actual Fabric: net.examplemod.fabric.ExampleExpectPlatformImpl#platformName()
     * Actual Forge: net.examplemod.forge.ExampleExpectPlatformImpl#platformName()
     * <p>
     * <a href="https://plugins.jetbrains.com/plugin/16210-architectury">You should also get the IntelliJ plugin to help with @ExpectPlatform.</a>
     */
    @ExpectPlatform
    public static String platformName() {
        throw new AssertionError("Platform-specific implementation missing!");
    }

    @ExpectPlatform
    public static BakedModel loadBakedModel(ResourceLocation resourceLocation){
        throw new AssertionError("Platform-specific implementation missing!");
    }

    @ExpectPlatform
    public static NonNullBiFunction<Block, Item.Properties, ? extends EngineOrderTelegraphBlockItem> getSpecificEngineOrderTelegraphBlockItemConstructor() {
        throw new AssertionError("Platform-specific implementation missing!");
    }
}
