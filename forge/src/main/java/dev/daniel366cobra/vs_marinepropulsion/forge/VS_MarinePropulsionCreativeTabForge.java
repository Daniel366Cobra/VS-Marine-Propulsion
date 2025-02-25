package dev.daniel366cobra.vs_marinepropulsion.forge;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionBlocks;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VS_MarinePropulsionCreativeTabForge {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, VS_MarinePropulsionMod.MOD_ID);

    public static final List<ItemProviderEntry<?>> ITEMS = List.of(
            VS_MarinePropulsionBlocks.LARGE_SHIP_PROPELLER,
            VS_MarinePropulsionBlocks.ENGINE_ORDER_TELEGRAPH,
            VS_MarinePropulsionBlocks.VARIATOR
    );

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.vs_marinepropulsion.main"))
                    .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
                    .icon(VS_MarinePropulsionBlocks.LARGE_SHIP_PROPELLER::asStack)
                    .displayItems(new DisplayItemsGenerator(ITEMS))
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    private record DisplayItemsGenerator(
            List<ItemProviderEntry<?>> items) implements CreativeModeTab.DisplayItemsGenerator {
        @Override
        public void accept(@NotNull CreativeModeTab.ItemDisplayParameters params,
                           @NotNull CreativeModeTab.Output output) {
            for (ItemProviderEntry<?> item : items) {
                output.accept(item);
            }
        }
    }
}
