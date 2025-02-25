package dev.daniel366cobra.vs_marinepropulsion.items;

import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.utility.LangBuilder;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionBlocks;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class EngineOrderTelegraphBlockItem extends BlockItem {

    public EngineOrderTelegraphBlockItem (Block block, Item.Properties properties){

        super(block,properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        LangBuilder langBuilder = new LangBuilder(VS_MarinePropulsionMod.MOD_ID);
        ItemStack stack = pContext.getItemInHand();
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState state = level.getBlockState(pos);
        Player player = pContext.getPlayer();

        boolean clickedVariator = VS_MarinePropulsionBlocks.VARIATOR.has(state);

        if (player == null)
            return InteractionResult.FAIL;

        if (player.isShiftKeyDown() && stack.hasTag()) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            player.displayClientMessage(langBuilder.translate("engine_order_telegraph.clear").component()
                    .withStyle(ChatFormatting.WHITE), true);
            stack.setTag(null);
            return InteractionResult.SUCCESS;
        }

        if (clickedVariator) {
            if (level.isClientSide) return InteractionResult.SUCCESS;

            CompoundTag stackTag = stack.getOrCreateTag();

            ListTag savedPositions = stackTag.getList("LinkedVariatorsPos", Tag.TAG_COMPOUND);

            boolean alreadySaved = false;

            for (Tag tag : savedPositions) {
                BlockPos savedPos = NbtUtils.readBlockPos((CompoundTag) tag);
                if (savedPos.equals(pos)) {
                    alreadySaved = true;
                    break;
                }
            }

            if (alreadySaved) {
                ListTag newSavedPositions = new ListTag();
                for (Tag tag : savedPositions) {
                    BlockPos savedPos = NbtUtils.readBlockPos((CompoundTag) tag);
                    if (!savedPos.equals(pos)) {
                        newSavedPositions.add(tag);
                    }
                }
                stackTag.put("LinkedVariatorsPos", newSavedPositions);
                player.displayClientMessage(langBuilder.translate("engine_order_telegraph.remove").component()
                        .withStyle(ChatFormatting.WHITE), true);
            } else {
                savedPositions.add(NbtUtils.writeBlockPos(pos));
                stackTag.put("LinkedVariatorsPos", savedPositions);
                player.displayClientMessage(langBuilder.translate("engine_order_telegraph.add").component()
                        .withStyle(ChatFormatting.WHITE), true);

            }

            stack.setTag(stackTag);
            return InteractionResult.SUCCESS;

        } else {

            CompoundTag stackTag = stack.getTag();

            if (stackTag != null) {
                stackTag.put("BlockEntityTag", stackTag.copy());
            }

            InteractionResult useOn = super.useOn(pContext);
            if (level.isClientSide || useOn == InteractionResult.FAIL)
                return useOn;


            ItemStack itemInHand = player.getItemInHand(pContext.getHand());
            if (!itemInHand.isEmpty())
                itemInHand.setTag(null);
            player.displayClientMessage(langBuilder.translate("engine_order_telegraph.success").component()
                    .withStyle(ChatFormatting.GREEN), true);

            return useOn;
        }
    }

    private static List<BlockPos> lastShownPosList = new ArrayList<>();
    private static List<AABB> lastShownAABBList = new ArrayList<>();

    public static void clientTick() {
        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;
        ItemStack heldItemMainhand = player.getMainHandItem();
        if (!(heldItemMainhand.getItem() instanceof EngineOrderTelegraphBlockItem))
            return;
        if (!heldItemMainhand.hasTag())
            return;
        CompoundTag stackTag = heldItemMainhand.getOrCreateTag();
        if (!stackTag.contains("LinkedVariatorsPos"))
            return;

        ListTag selectedPosListTag = stackTag.getList("LinkedVariatorsPos", Tag.TAG_COMPOUND);
        List<BlockPos> selectedPosList = new ArrayList<>();

        //a List of all selected BlockPos
        selectedPosListTag.forEach(tag -> selectedPosList
                .add(NbtUtils.readBlockPos((CompoundTag) tag)));

        if (!selectedPosList.equals(lastShownPosList)) {
            selectedPosList.forEach(pos -> lastShownAABBList.add(getBounds(pos)));
            lastShownPosList = selectedPosList;
        }

        lastShownAABBList.forEach(aabb -> {
            CreateClient.OUTLINER.showAABB("target", aabb)
                    .colored(0xffcb74)
                    .lineWidth(1 / 16f);
        });
    }

    protected static AABB getBounds(BlockPos pos) {
        Level world = Minecraft.getInstance().level;

        BlockState state = world.getBlockState(pos);
        VoxelShape shape = state.getShape(world, pos);
        return shape.isEmpty() ? new AABB(BlockPos.ZERO)
                : shape.bounds()
                .move(pos);
    }


}
