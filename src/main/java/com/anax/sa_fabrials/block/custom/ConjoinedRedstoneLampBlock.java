package com.anax.sa_fabrials.block.custom;

import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.entity.custom.ConjoinedRedstoneLampBlockEntity;
import com.anax.sa_fabrials.block.entity.custom.CrystalBlockEntity;
import com.anax.sa_fabrials.item.SAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ConjoinedRedstoneLampBlock extends BaseEntityBlock {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ConjoinedRedstoneLampBlockEntity) {
                ConjoinedRedstoneLampBlockEntity entity = (ConjoinedRedstoneLampBlockEntity) blockEntity;
                if(entity.getPairPos() != null){
                    ConjoinedRedstoneLampBlockEntity pairEntity;
                    if(pLevel.getBlockEntity(entity.getPairPos()) instanceof ConjoinedRedstoneLampBlockEntity){
                        pairEntity = (ConjoinedRedstoneLampBlockEntity) pLevel.getBlockEntity(entity.getPairPos());
                        if(pairEntity.pairId.equals(entity.pairId)){
                            ItemStack toDrop = SAItems.CONJOINED_REDSTONE_LAMP_PLACER.get().getDefaultInstance();
                            toDrop.getOrCreateTag().putLong("sa_fabrials.pair_id", entity.pairId);
                            toDrop.getOrCreateTag().putInt("sa_fabrials.pair_x", entity.getPairPos().getX());
                            toDrop.getOrCreateTag().putInt("sa_fabrials.pair_y", entity.getPairPos().getY());
                            toDrop.getOrCreateTag().putInt("sa_fabrials.pair_z", entity.getPairPos().getZ());
                            toDrop.getOrCreateTag().putBoolean("sa_fabrials.is_pair_null", false);

                            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), toDrop));
                            BlockPos pairPos = entity.getPairPos();
                            if(pLevel.getBlockState(pairPos).is(ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get())){
                                ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get().neighborChanged(pLevel.getBlockState(pairPos), pLevel, pairPos, ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get(), null, false);
                            }

                            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
                            return;
                        }
                    }
                }
                super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
                return;

            }
        }

    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    public ConjoinedRedstoneLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
        if (!level.isClientSide) {
            boolean flag = blockState.getValue(LIT);
            BlockPos pair = null;
            if(level.getBlockEntity(blockPos) instanceof ConjoinedRedstoneLampBlockEntity){
                pair = ((ConjoinedRedstoneLampBlockEntity) level.getBlockEntity(blockPos)).getPairPos();
            }
            boolean hasNeighbourSignal = false;
            if(level.hasNeighborSignal(blockPos)){
                hasNeighbourSignal = true;
            }
            if(pair != null){
                BlockEntity pairEntity = level.getBlockEntity(pair);
                if(pairEntity instanceof ConjoinedRedstoneLampBlockEntity){
                    if(!((ConjoinedRedstoneLampBlockEntity) pairEntity).pairId.equals(((ConjoinedRedstoneLampBlockEntity) level.getBlockEntity(blockPos)).pairId)){
                        pair = null;
                    }
                }
            }
            if(pair != null){
                hasNeighbourSignal |= level.hasNeighborSignal(pair);
            }

            if (flag != hasNeighbourSignal) {
                if (flag) {
                    level.scheduleTick(blockPos, this, 4);
                    if(pair != null){
                        level.scheduleTick(pair, this, 4);
                    }
                } else {
                    if(pair != null){
                        level.setBlock(pair, blockState.cycle(LIT), 2);
                    }
                    level.setBlock(blockPos, blockState.cycle(LIT), 2);
                }
            }
        }
    }
    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        BlockPos pair = null;
        boolean hasNeighborSignal = serverLevel.hasNeighborSignal(blockPos);
        if(serverLevel.getBlockEntity(blockPos) instanceof  ConjoinedRedstoneLampBlockEntity){
            pair = ((ConjoinedRedstoneLampBlockEntity) serverLevel.getBlockEntity(blockPos)).getPairPos();
        }
        if(pair != null){
            BlockEntity pairEntity = serverLevel.getBlockEntity(pair);
            if(pairEntity instanceof ConjoinedRedstoneLampBlockEntity){
                if(((ConjoinedRedstoneLampBlockEntity) pairEntity).pairId.equals(((ConjoinedRedstoneLampBlockEntity) serverLevel.getBlockEntity(blockPos)).pairId)){
                    hasNeighborSignal |= serverLevel.hasNeighborSignal(pair);
                }
            }
        }

        if (blockState.getValue(LIT) && !hasNeighborSignal) {
            serverLevel.setBlock(blockPos, blockState.cycle(LIT), 2);
            if(pair != null){serverLevel.setBlock(pair, blockState.cycle(LIT), 2);}
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConjoinedRedstoneLampBlockEntity(blockPos, blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        CompoundTag tag = blockPlaceContext.getItemInHand().getOrCreateTag();
        boolean isLit = false;
        if(tag.contains("sa_fabrials.is_pair_null") && tag.contains("sa_fabrials.pair_x") && tag.contains("sa_fabrials.pair_y") && tag.contains("sa_fabrials.pair_z") && tag.contains("sa_fabrials.pair_id")){
            if(!tag.getBoolean("sa_fabrials.is_pair_null")){
                BlockPos pair = new BlockPos(tag.getInt("sa_fabrials.pair_x"), tag.getInt("sa_fabrials.pair_y"), tag.getInt("sa_fabrials.pair_z"));
                if(blockPlaceContext.getLevel().getBlockState(pair).is(ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get())){
                    BlockEntity entity = blockPlaceContext.getLevel().getBlockEntity(pair);
                    if(entity instanceof ConjoinedRedstoneLampBlockEntity){
                        if(((ConjoinedRedstoneLampBlockEntity) entity).pairId == tag.getLong("sa_fabrials.pair_id")){
                            isLit = blockPlaceContext.getLevel().getBlockState(pair).getValue(ConjoinedRedstoneLampBlock.LIT);
                        }
                    }

                }
            }
        }
        return ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get().defaultBlockState().setValue(ConjoinedRedstoneLampBlock.LIT, Boolean.valueOf(isLit));
    }
}
