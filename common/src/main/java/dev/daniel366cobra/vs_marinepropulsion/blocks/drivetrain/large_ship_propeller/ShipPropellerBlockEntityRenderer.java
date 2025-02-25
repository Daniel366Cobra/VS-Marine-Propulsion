package dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ShipPropellerBlockEntityRenderer extends KineticBlockEntityRenderer<ShipPropellerBlockEntity> {

    public ShipPropellerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(ShipPropellerBlockEntity be, BlockState state) {
        return CachedBufferer.partialFacing(VS_MarinePropulsionPartialModels.LARGE_SHIP_PROPELLER, state);
    }

    @Override
    protected void renderSafe(ShipPropellerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (Backend.canUseInstancing(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        float speed = be.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = be.angle + speed * partialTicks;

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        renderPropeller(be, ms, light, blockState, angle, vb);
    }

    private void renderPropeller(ShipPropellerBlockEntity be, PoseStack ms, int light, BlockState blockState, float angle,
                                 VertexConsumer vb) {
        SuperByteBuffer propeller = CachedBufferer.partialFacing(VS_MarinePropulsionPartialModels.LARGE_SHIP_PROPELLER, blockState);
        kineticRotationTransform(propeller, be, getRotationAxisOf(be), AngleHelper.rad(angle), light);
        propeller.renderInto(ms, vb);
    }



    @Override
    protected BlockState getRenderedBlockState(ShipPropellerBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }

}
