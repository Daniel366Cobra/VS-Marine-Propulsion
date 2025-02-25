package dev.daniel366cobra.vs_marinepropulsion.blocks.controls.engine_order_telegraph;

import com.simibubi.create.content.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.LangBuilder;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator.VariatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.stream.Collectors;

public class EngineOrderTelegraphBlockEntity extends SmartBlockEntity implements IHaveHoveringInformation {

    int throttleOrder = 0;
    int changeTimer;
    LerpedFloat clientState;

    private Set<BlockPos> linkedVariatorBlockPos = new HashSet<>();

    public EngineOrderTelegraphBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        clientState = LerpedFloat.linear();
    }

    @Override
    public void write(CompoundTag tag, boolean clientPacket) {
        VS_MarinePropulsionMod.LOGGER.info("Writing EOT Block Entity!");

        super.write(tag, clientPacket);

        tag.putInt("ThrottleOrder", throttleOrder);
        tag.putInt("ChangeTimer", changeTimer);

        ListTag linkedVariatorsTag = new ListTag();
        linkedVariatorBlockPos.forEach(blockPos -> linkedVariatorsTag.add(NbtUtils.writeBlockPos(blockPos)));
        tag.put("LinkedVariatorsPos", linkedVariatorsTag);
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {

        //VS_MarinePropulsionMod.LOGGER.info("Reading EOT Block Entity!");

        super.read(tag, clientPacket);
        throttleOrder = tag.getInt("ThrottleOrder");
        changeTimer = tag.getInt("ChangeTimer");

        ListTag linkedVariatorsTag = tag.getList("LinkedVariatorsPos", Tag.TAG_COMPOUND);

        linkedVariatorsTag.forEach(variatorTag -> linkedVariatorBlockPos
                .add(NbtUtils.readBlockPos((CompoundTag) variatorTag)));

        //Why is THIS.GETLEVEL() null? Loaded too early before the world is assigned?
        //Maybe fill the variators list later?

        clientState.chase(throttleOrder, 0.2f, LerpedFloat.Chaser.EXP);

    }

    @Override
    public void tick() {
        super.tick();

        if (changeTimer > 0) {
            changeTimer--;
            if (changeTimer == 0) {

                    updateVariators();
                    level.playSound(null, worldPosition, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 0.2F, 1.0f);

            }
        }
        if (level.isClientSide)
            clientState.tickChaser();
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    private void updateVariators() {
        if (!level.isLoaded(this.worldPosition)) return;
        if (level.isClientSide()) return;

        linkedVariatorBlockPos
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .forEach(blockPos ->
                        {
                                VariatorBlockEntity vbe = (VariatorBlockEntity) level.getBlockEntity(blockPos);
                                VS_MarinePropulsionMod.LOGGER.info("Updating throttle on Variator at " + blockPos.toShortString());
                                vbe.setThrottleOrder(this.throttleOrder);
                        });



    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    public void changeState(boolean back) {
        int prevState = throttleOrder;
        throttleOrder += back ? -1 : 1;
        throttleOrder = Mth.clamp(throttleOrder, -3, 3);
        if (prevState != throttleOrder)
            changeTimer = 15;

        sendData();
    }

    @Override
    public void remove() {
        super.remove();

        if (this.linkedVariatorBlockPos.isEmpty()) return;

        List<VariatorBlockEntity> linkedVariatorBlockEntities = new ArrayList<>();

        linkedVariatorBlockPos
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .forEach(blockPos -> {
                    VariatorBlockEntity vbe = (VariatorBlockEntity) level.getBlockEntity(blockPos);
                    vbe.removeMasterTelegraph();
                });
    }

    public void unlinkVariatorAt(BlockPos blockPos) {
        VS_MarinePropulsionMod.LOGGER.info("Unlinking Variator at: " + blockPos.toShortString());
        this.linkedVariatorBlockPos.remove(blockPos);
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        LangBuilder langBuilder = new LangBuilder(VS_MarinePropulsionMod.MOD_ID);

        langBuilder.add(Component.translatable("vs_marinepropulsion.tooltip.current_order"))
                .add(Component.translatable(EngineOrder.throttleDescription(this.throttleOrder)))
                .forGoggles(tooltip);

        return true;
    }

    public int getThrottleOrder() {
        return throttleOrder;
    }

    public enum EngineOrder {
        FULL_ASTERN(-3, "vs_marinepropulsion.tooltip.full_astern"),
        HALF_ASTERN(-2, "vs_marinepropulsion.tooltip.half_astern"),
        SLOW_ASTERN(-1, "vs_marinepropulsion.tooltip.slow_astern"),
        STOP(0, "vs_marinepropulsion.tooltip.stop"),
        SLOW_AHEAD(1, "vs_marinepropulsion.tooltip.slow_ahead"),
        HALF_AHEAD(2, "vs_marinepropulsion.tooltip.half_ahead"),
        FULL_AHEAD(3, "vs_marinepropulsion.tooltip.full_ahead");

        private final int orderThrottle;
        private final String orderDescription;

        EngineOrder(int orderThrottle, String orderDescription) {
            this.orderThrottle = orderThrottle;
            this.orderDescription = orderDescription;
        }

        public int getThrottle() {
            return this.orderThrottle;
        }

        public static String throttleDescription(int order) {
            for (EngineOrder eo : EngineOrder.values()) {
                if (eo.orderThrottle == order) {
                    return eo.orderDescription;
                }
            }
            throw new IllegalArgumentException("Invalid engine order: " + order);
        }
    }

}
