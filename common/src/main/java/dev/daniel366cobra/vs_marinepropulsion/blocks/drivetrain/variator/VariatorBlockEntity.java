package dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionMod;
import dev.daniel366cobra.vs_marinepropulsion.blocks.controls.engine_order_telegraph.EngineOrderTelegraphBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator.VariatorBlock.LINKED;
import static dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator.VariatorBlock.ORDER;
import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class VariatorBlockEntity extends SplitShaftBlockEntity implements IEngineTelegraphControllable {

    private BlockPos masterTelegraphBlockPos = null;
    private int throttleOrder = 0;



    public VariatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (this.masterTelegraphBlockPos != null)
            tag.put("MasterTelegraphPos", NbtUtils.writeBlockPos(this.masterTelegraphBlockPos));
    }

    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (tag.contains("MasterTelegraphPos"))
            this.masterTelegraphBlockPos = NbtUtils.readBlockPos(tag.getCompound("MasterTelegraphPos"));
        else this.masterTelegraphBlockPos = null;
    }



    @Override
    public float getRotationSpeedModifier(Direction face) {
        VS_MarinePropulsionMod.LOGGER.info("Variator at " + this.getBlockPos().toShortString() + " changing multiplier to " + throttleOrder / 3.0f);
        if (this.hasSource() && face != this.getSourceFacing()){
            if (this.masterTelegraphBlockPos != null)
                return throttleOrder / 3.0f;
            else
                return 0;
        } else {
            return 1;
        }
    }

    public void setMasterTelegraph(BlockPos blockPos) {
        this.masterTelegraphBlockPos = blockPos;
        if (level != null) {
            VS_MarinePropulsionMod.LOGGER.info("Linking telegraph at: " + this.getBlockPos().toShortString());
            level.setBlock(getBlockPos(), getBlockState().setValue(LINKED, true), UPDATE_ALL);
        }

    }

    public void removeMasterTelegraph() {
        if (this.masterTelegraphBlockPos != null
                && level.getBlockEntity(this.masterTelegraphBlockPos) instanceof EngineOrderTelegraphBlockEntity masterTelegraphBlockEntity) {
            masterTelegraphBlockEntity.unlinkVariatorAt(this.getBlockPos());
        }
        this.masterTelegraphBlockPos = null;
        this.throttleOrder = 0;
        if (level != null) {
            level.setBlock(getBlockPos(), getBlockState().setValue(LINKED, false).setValue(ORDER, 3), UPDATE_ALL);
        }
    }


    public void setThrottleOrder(int throttleOrder) {
        //VS_MarinePropulsionMod.LOGGER.info("Received Variator Throttle order update at: " + this.getBlockPos().toShortString() + ", new throttle is " + throttleOrder);
        if (level != null) {
                this.throttleOrder = throttleOrder;
                detachKinetics();
                level.setBlock(getBlockPos(), getBlockState().setValue(ORDER, throttleOrder + 3), UPDATE_ALL);
                attachKinetics();
                notifyUpdate();

        }
    }

    public int getThrottleOrder() {
        return this.throttleOrder;
    }

    @Override
    public void remove() {
        this.removeMasterTelegraph();
        super.remove();
    }

    @Override
    public void destroy() {
        this.removeMasterTelegraph();
        super.destroy();
    }

}
