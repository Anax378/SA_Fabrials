package com.anax.sa_fabrials.block.custom;

import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.entity.custom.ArtifabriansStationBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ArtifabriansStationBlock extends BaseEntityBlock {

    public static VoxelShape SHAPE = Stream.of(
            Block.box(1, 3, 1, 15, 10, 15),
            Block.box(12, 0, 12, 16, 4, 16),
            Block.box(0, 0, 12, 4, 4, 16),
            Block.box(12, 0, 0, 16, 4, 4),
            Block.box(0, 0, 0, 4, 4, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public ArtifabriansStationBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArtifabriansStationBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }


    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState1, boolean b) {
        if(level.getBlockEntity(blockPos) instanceof ArtifabriansStationBlockEntity){
            ((ArtifabriansStationBlockEntity)level.getBlockEntity(blockPos)).drops();
        }
        super.onRemove(blockState, level, blockPos, blockState1, b);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> tBlockEntityType) {
        return createTickerHelper(tBlockEntityType, ModBlockEntities.ARTIFABRIANS_STATION_BLOCK_ENTITY.get(), ArtifabriansStationBlockEntity::tick);
    }
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(blockPos);
            if(entity instanceof ArtifabriansStationBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player,(ArtifabriansStationBlockEntity) entity, blockPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

}
