package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.custom.ConjoinedRedstoneLampBlock;
import com.anax.sa_fabrials.block.entity.custom.ConjoinedRedstoneLampBlockEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import java.nio.ReadOnlyBufferException;
import java.util.Random;

public class ConjoinedRedstoneLampPlacerItem extends Item {

    public ConjoinedRedstoneLampPlacerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        if(!useOnContext.getLevel().isClientSide) {
            BlockPlaceContext placeContext = new BlockPlaceContext(useOnContext);
            if (placeContext.canPlace()) {
                BlockState state = ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get().getStateForPlacement(placeContext);
                if (state != null && this.canPlace(placeContext, state)) {
                    if (placeBlock(placeContext, state)) {
                        BlockEntity entity = useOnContext.getLevel().getBlockEntity(placeContext.getClickedPos());
                        if (entity instanceof ConjoinedRedstoneLampBlockEntity) {
                            CompoundTag placerItemTag = useOnContext.getItemInHand().getOrCreateTag();
                            boolean pairExists = false;
                            BlockPos pair = null;
                            if (placerItemTag.contains("sa_fabrials.is_pair_null") && !placerItemTag.getBoolean("sa_fabrials.is_pair_null") && placerItemTag.contains("sa_fabrials.pair_x") && placerItemTag.contains("sa_fabrials.pair_y") && placerItemTag.contains("sa_fabrials.pair_z") && placerItemTag.contains("sa_fabrials.pair_id")) {
                                pair = new BlockPos(placerItemTag.getInt("sa_fabrials.pair_x"), placerItemTag.getInt("sa_fabrials.pair_y"), placerItemTag.getInt("sa_fabrials.pair_z"));
                                if (useOnContext.getLevel().getBlockState(pair).is(ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get())) {
                                    BlockEntity pairEntity = useOnContext.getLevel().getBlockEntity(pair);
                                    if (pairEntity instanceof ConjoinedRedstoneLampBlockEntity) {
                                        if (((ConjoinedRedstoneLampBlockEntity) pairEntity).pairId == placerItemTag.getLong("sa_fabrials.pair_id")) {
                                            pairExists = true;
                                            long pairId = useOnContext.getItemInHand().getOrCreateTag().getLong("sa_fabrials.pair_id");

                                            ((ConjoinedRedstoneLampBlockEntity) entity).setPairPos(pair);
                                            ((ConjoinedRedstoneLampBlockEntity) entity).pairId = pairId;

                                            ((ConjoinedRedstoneLampBlockEntity) pairEntity).setPairPos(placeContext.getClickedPos());
                                            ((ConjoinedRedstoneLampBlockEntity) pairEntity).pairId = pairId;

                                            useOnContext.getItemInHand().getOrCreateTag().putBoolean("sa_fabrials.is_pair_null", true);
                                            useOnContext.getItemInHand().setCount(0);
                                            if(useOnContext.getPlayer() != null){
                                                useOnContext.getPlayer().getInventory().removeItem(useOnContext.getItemInHand());
                                            }
                                            ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get().neighborChanged(useOnContext.getLevel().getBlockState(placeContext.getClickedPos()), useOnContext.getLevel(), placeContext.getClickedPos(), ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get(), null, false);
                                            return InteractionResult.SUCCESS;
                                        }
                                    }

                                }
                            }
                            if (!pairExists) {
                                long randomId = new Random().nextLong();
                                ((ConjoinedRedstoneLampBlockEntity) entity).setPairPos(null);
                                useOnContext.getItemInHand().getOrCreateTag().putBoolean("sa_fabrials.is_pair_null", false);
                                useOnContext.getItemInHand().getOrCreateTag().putInt("sa_fabrials.pair_x", placeContext.getClickedPos().getX());
                                useOnContext.getItemInHand().getOrCreateTag().putInt("sa_fabrials.pair_y", placeContext.getClickedPos().getY());
                                useOnContext.getItemInHand().getOrCreateTag().putInt("sa_fabrials.pair_z", placeContext.getClickedPos().getZ());

                                useOnContext.getItemInHand().getOrCreateTag().putLong("sa_fabrials.pair_id", randomId);
                                ((ConjoinedRedstoneLampBlockEntity) entity).pairId = randomId;
                                ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get().neighborChanged(useOnContext.getLevel().getBlockState(placeContext.getClickedPos()), useOnContext.getLevel(), placeContext.getClickedPos(), ModBlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get(), null, false);
                                return InteractionResult.SUCCESS;
                            }
                        }

                    } else {
                        return InteractionResult.FAIL;
                    }
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                return InteractionResult.FAIL;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.CONSUME;
    }

    protected boolean placeBlock(BlockPlaceContext blockPlaceContext, BlockState blockState) {
        return blockPlaceContext.getLevel().setBlock(blockPlaceContext.getClickedPos(), blockState, 11);
    }

    protected boolean canPlace(BlockPlaceContext blockPlaceContext, BlockState blockState) {
        Player player = blockPlaceContext.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (blockState.canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) && blockPlaceContext.getLevel().isUnobstructed(blockState, blockPlaceContext.getClickedPos(), collisioncontext);
    }
}
