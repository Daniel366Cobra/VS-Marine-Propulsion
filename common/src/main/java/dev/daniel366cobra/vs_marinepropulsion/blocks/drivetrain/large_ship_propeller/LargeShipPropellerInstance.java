package dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionPartialModels;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class LargeShipPropellerInstance extends KineticBlockEntityInstance<ShipPropellerBlockEntity> implements DynamicInstance {

    protected final RotatingData shaft;
    protected final ModelData propeller;
    final Direction direction;
    private final Direction opposite;
    protected float lastAngle = Float.NaN;

    public LargeShipPropellerInstance(MaterialManager materialManager, ShipPropellerBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        direction = blockState.getValue(FACING);

        opposite = direction.getOpposite();
        shaft = setup(getRotatingMaterial().getModel(AllPartialModels.SHAFT_HALF, blockState, direction).createInstance());
        propeller = getTransformMaterial()
                .getModel(VS_MarinePropulsionPartialModels.LARGE_SHIP_PROPELLER, blockState, direction)
                .createInstance();

        animate(blockEntity.angle);
    }

    @Override
    public void update() {
        updateRotation(shaft);
    }

    @Override
    public void beginFrame() {

        float partialTicks = AnimationTickHolder.getPartialTicks();

        float speed = blockEntity.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = blockEntity.angle + speed * partialTicks;

        if (Math.abs(angle - lastAngle) < 0.001)
            return;

        animate(angle);

        lastAngle = angle;
    }

    private void animate(float angle) {
        PoseStack ms = new PoseStack();
        TransformStack msr = TransformStack.cast(ms);

        msr.translate(getInstancePosition());
        msr.centre()
                .rotate(Direction.get(Direction.AxisDirection.POSITIVE, axis), AngleHelper.rad(angle))
                .unCentre();

        propeller.setTransform(ms);
    }

    @Override
    public void updateLight() {
        BlockPos behind = pos.relative(opposite);
        relight(behind, shaft);

        BlockPos inFront = pos.relative(direction);
        relight(inFront, propeller);
    }

    @Override
    public void remove() {
        shaft.delete();
        propeller.delete();
    }

}
