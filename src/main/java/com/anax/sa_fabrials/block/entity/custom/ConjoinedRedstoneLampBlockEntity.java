package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.SABlocks;
import com.anax.sa_fabrials.block.entity.SABlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ConjoinedRedstoneLampBlockEntity extends BlockEntity {
    BlockPos pair;
    public Long pairId;
    public ConjoinedRedstoneLampBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SABlockEntities.CONJOINED_REDSTONE_LAMP_BLOCK_ENTITY.get(), blockPos, blockState);
        pair = null;
        pairId = 0L;
    }

    @Nullable
    public BlockPos getPairPos(){
        if(pair == null){return null;}
        if(this.getLevel().getBlockState(pair).is(SABlocks.CONJOINED_REDSTONE_LAMP_BLOCK.get())){
            return pair;
        }
        return null;
    }
    public void setPairPos(BlockPos pair){
        this.pair = pair;
    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        if(pair != null) {
            tag.putInt("sa_fabrials.pair_x", pair.getX());
            tag.putInt("sa_fabrials.pair_y", pair.getY());
            tag.putInt("sa_fabrials.pair_z", pair.getZ());
            tag.putBoolean("sa_fabrials.is_pair_null", false);
        }else{
            tag.putInt("sa_fabrials.pair_x", 0);
            tag.putInt("sa_fabrials.pair_y", 0);
            tag.putInt("sa_fabrials.pair_z", 0);
            tag.putBoolean("sa_fabrials.is_pair_null", true);
        }
        tag.putLong("sa_fabrials.pair_id", pairId);
    }

    @Override
    public void load(CompoundTag tag) {
        if(!tag.contains("sa_fabrials.is_pair_null") || tag.getBoolean("sa_fabrials.is_pair_null")){
            pair = null;
        }else if (tag.contains("sa_fabrials.pair_x") && tag.contains("sa_fabrials.pair_y") && tag.contains("sa_fabrials.pair_z")){
            pair = new BlockPos(tag.getInt("sa_fabrials.pair_x"), tag.getInt("sa_fabrials.pair_y"), tag.getInt("sa_fabrials.pair_z"));
        }else{
            pair = null;
        }
        if(tag.contains("sa_fabrials.pair_id")){
            pairId = tag.getLong("sa_fabrial.pair_id");
        }
    }

}
