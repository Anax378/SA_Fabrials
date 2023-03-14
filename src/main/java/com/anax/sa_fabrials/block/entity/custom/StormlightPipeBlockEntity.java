package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class StormlightPipeBlockEntity extends BlockEntity {

    static Map<Direction, Integer> directionToIndex = Map.of(
            Direction.DOWN, 0,
            Direction.UP, 1,
            Direction.NORTH, 2,
            Direction.SOUTH, 3,
            Direction.WEST, 4,
            Direction.EAST, 5
    );

    public StormlightStorage stormlightStorage;
    private LazyOptional<StormlightStorage> lazyStormlightStorage = LazyOptional.empty();

    public StormlightPipeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.STORMLIGHT_PIPE_BLOCK_ENTITY.get(), blockPos, blockState);
        stormlightStorage = new StormlightStorage(1050, 10000, 10000, 0) {
            @Override public void onChanged() {setChanged();}};
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if(cap == StormlightStorage.STORMLIGHT_STORAGE){
            return lazyStormlightStorage.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyStormlightStorage.invalidate();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyStormlightStorage = LazyOptional.of(() -> stormlightStorage);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.merge(stormlightStorage.saveOnNBT());
    }
    @Override
    @NotNull
    public BlockEntityType<?> getType(){
        return ModBlockEntities.STORMLIGHT_PIPE_BLOCK_ENTITY.get();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        stormlightStorage.loadFromNBT(compoundTag);
    }

    public void handleStormlight(Level level, BlockPos pos, Direction dir){
        BlockEntity targetEntity = level.getBlockEntity(pos);
        if(targetEntity != null){
            int[] received = {0};
            targetEntity.getCapability(StormlightStorage.STORMLIGHT_STORAGE, dir).ifPresent(
                    handler -> {
                        if(handler.canReceive()){
                            received[0] = handler.receiveStormlight(this.stormlightStorage.extractStormlight(10000, true), false);
                        }
                    }
            );
            this.stormlightStorage.extractStormlight(received[0], false);
        }
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, StormlightPipeBlockEntity pBlockEntity){
        pBlockEntity.handleStormlight(pLevel, pPos.below(), Direction.UP);
        pBlockEntity.handleStormlight(pLevel, pPos.above(), Direction.DOWN);
        pBlockEntity.handleStormlight(pLevel, pPos.north(), Direction.SOUTH);
        pBlockEntity.handleStormlight(pLevel, pPos.south(), Direction.NORTH);
        pBlockEntity.handleStormlight(pLevel, pPos.east(), Direction.WEST);
        pBlockEntity.handleStormlight(pLevel, pPos.west(), Direction.EAST);
    }
}
