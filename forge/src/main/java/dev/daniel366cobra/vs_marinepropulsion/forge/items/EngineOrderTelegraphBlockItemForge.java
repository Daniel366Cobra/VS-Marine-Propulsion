package dev.daniel366cobra.vs_marinepropulsion.forge.items;

import com.simibubi.create.CreateClient;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionBlocks;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EngineOrderTelegraphBlockItemForge extends EngineOrderTelegraphBlockItem {

    public EngineOrderTelegraphBlockItemForge(Block pBlock, Properties pProperties) {

        super(pBlock, pProperties);
    }

    @SubscribeEvent
    public static void gathererItemAlwaysPlacesWhenUsed(PlayerInteractEvent.RightClickBlock event) {
        ItemStack usedItem = event.getItemStack();
        if (usedItem.getItem() instanceof EngineOrderTelegraphBlockItem) {
            if (VS_MarinePropulsionBlocks.ENGINE_ORDER_TELEGRAPH.has(event.getLevel().getBlockState(event.getPos())))
                return;
        event.setUseBlock(Event.Result.DENY);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientTick() {
        EngineOrderTelegraphBlockItem.clientTick();
    }

    @OnlyIn(Dist.CLIENT)
    protected static AABB getBounds(BlockPos pos) {
        return EngineOrderTelegraphBlockItem.getBounds(pos);
    }

}
