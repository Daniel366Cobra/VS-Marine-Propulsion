package dev.daniel366cobra.vs_marinepropulsion.fabric.items;

import com.simibubi.create.CreateClient;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionBlocks;
import dev.daniel366cobra.vs_marinepropulsion.items.EngineOrderTelegraphBlockItem;
import io.github.fabricators_of_create.porting_lib.item.BlockUseBypassingItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EngineOrderTelegraphBlockItemFabric extends EngineOrderTelegraphBlockItem implements BlockUseBypassingItem {

    public EngineOrderTelegraphBlockItemFabric(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean shouldBypass(BlockState state, BlockPos pos, Level level, Player player, InteractionHand hand) {
        ItemStack usedItem = player.getItemInHand(hand);
        if (usedItem.getItem() instanceof EngineOrderTelegraphBlockItemFabric) {
            return state.getBlock() == VS_MarinePropulsionBlocks.VARIATOR.get();
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public static void clientTick() {
        EngineOrderTelegraphBlockItem.clientTick();
    }

    @Environment(EnvType.CLIENT)
    protected static AABB getBounds(BlockPos pos) {
        return EngineOrderTelegraphBlockItem.getBounds(pos);
    }
}
