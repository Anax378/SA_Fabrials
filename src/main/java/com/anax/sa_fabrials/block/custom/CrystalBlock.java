package com.anax.sa_fabrials.block.custom;

import com.anax.sa_fabrials.block.entity.custom.CrystalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class CrystalBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public CrystalBlock(Properties properties) {
        super(properties);
    }
    abstract BlockEntity getNewBlockENtity(BlockPos blockPos, BlockState blockState);
    @Nullable
    @Override

    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return getNewBlockENtity(blockPos, blockState);
    }

    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 13, 13);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation){
        return pState.setValue(FACING, pRotation.rotate((pState.getValue(FACING))));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror){
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrystalBlockEntity) {
                ((CrystalBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }


    abstract boolean isMyType(BlockEntity entity);
    abstract void openGui(ServerPlayer player,BlockEntity entity ,BlockPos pPos);
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            System.out.println("use");
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(isMyType(entity)) {
                openGui(((ServerPlayer)pPlayer), entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    abstract <T extends BlockEntity> BlockEntityTicker<T> buildCreateTickHelper (BlockEntityType<T> pBlockEntityType);

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return buildCreateTickHelper(pBlockEntityType);
    }
}
