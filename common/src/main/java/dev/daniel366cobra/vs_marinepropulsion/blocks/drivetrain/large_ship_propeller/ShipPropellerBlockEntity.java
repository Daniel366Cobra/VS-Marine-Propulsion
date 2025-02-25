package dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ShipPropellerBlockEntity extends KineticBlockEntity {

    private boolean updateThrust = true;
    private int thrustUpdateCooldown = 0;

    public LerpedFloat visualSpeed = LerpedFloat.linear();
    public float angle;

    public ShipPropellerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (clientPacket)
            visualSpeed.chase(getGeneratedSpeed(), 1 / 64f, LerpedFloat.Chaser.EXP);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
    }

    public Direction getThrustOriginSide() {
        return this.getBlockState()
                .getValue(BlockStateProperties.FACING);
    }

    public Direction getThrustDirection() {
        float speed = getSpeed();
        if (speed == 0)
            return null;
        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);
        speed = convertToDirection(speed, facing);
        return speed > 0 ? facing : facing.getOpposite();
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        updateThrust = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide || isVirtual()) {
            float targetSpeed = this.getSpeed();
            visualSpeed.updateChaseTarget(targetSpeed);
            visualSpeed.tickChaser();
            angle += visualSpeed.getValue() * 3 / 10f;
            angle %= 360;
        }
        else {
            if (thrustUpdateCooldown-- <= 0) {
                thrustUpdateCooldown = 5;
                updateThrust = true;
            }

            if (updateThrust) {
                updateThrust = false;
                //Update Thrust
                sendData();
            }
        }


    }

}
