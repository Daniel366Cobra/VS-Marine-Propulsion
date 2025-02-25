package dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.large_ship_propeller;


import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.daniel366cobra.vs_marinepropulsion.VS_MarinePropulsionBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class LargeShipPropellerBlock extends DirectionalKineticBlock implements IBE<ShipPropellerBlockEntity> {

    public LargeShipPropellerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return super.getMinimumRequiredSpeedLevel();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction preferredFacing = getPreferredFacing(context);
        if (preferredFacing == null)
            preferredFacing = context.getNearestLookingDirection();

        return defaultBlockState().setValue(FACING, context.getPlayer() != null && context.getPlayer()
                .isShiftKeyDown() ? preferredFacing.getOpposite() : preferredFacing);
    }

    @Override
    public Class<ShipPropellerBlockEntity> getBlockEntityClass() {
        return ShipPropellerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ShipPropellerBlockEntity> getBlockEntityType() {
        return VS_MarinePropulsionBlockEntities.LARGE_SHIP_PROPELLER_BLOCK_ENTITY.get();
    }
}
