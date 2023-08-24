package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.custom.StormlightPipeBlock;
import com.anax.sa_fabrials.block.entity.SABlockEntities;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class StormlightPipeBlockEntity extends BlockEntity {

    public static Map<Direction, BooleanProperty> isInMap = Map.ofEntries(
            Map.entry(Direction.UP, StormlightPipeBlock.IS_INPUT_UP),
            Map.entry(Direction.DOWN, StormlightPipeBlock.IS_INPUT_DOWN),
            Map.entry(Direction.EAST, StormlightPipeBlock.IS_INPUT_EAST),
            Map.entry(Direction.WEST, StormlightPipeBlock.IS_INPUT_WEST),
            Map.entry(Direction.NORTH, StormlightPipeBlock.IS_INPUT_NORTH),
            Map.entry(Direction.SOUTH, StormlightPipeBlock.IS_INPUT_SOUTH)
    );

    public StormlightStorage stormlightStorage;
    private LazyOptional<StormlightStorage> lazyStormlightStorage = LazyOptional.empty();

    public StormlightPipeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SABlockEntities.STORMLIGHT_PIPE_BLOCK_ENTITY.get(), blockPos, blockState);
        stormlightStorage = new StormlightStorage(1050, 10000, 10000, 0) {
            @Override public void onChanged() {setChanged();}

            @Override
            public boolean canExtract(@Nullable Direction direction) {
                if(getBlockState().getValue(isInMap.get(direction))){return false;}
                return super.canExtract(direction);
            }

            @Override
            public boolean canReceive(@Nullable Direction direction) {
                if(!getBlockState().getValue(isInMap.get(direction))){return false;}
                return super.canReceive(direction);
            }
        };
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
        return SABlockEntities.STORMLIGHT_PIPE_BLOCK_ENTITY.get();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        stormlightStorage.loadFromNBT(compoundTag);
    }
    public static int[] getDistribution(int m, int[] a){
        int[][] b = new int[6][2];
        for (int i = 0; i < 6; i++){
            if(i < a.length){
                b[i][0] = a[i];
            }else{
                b[i][0] = 0;
            }
            b[i][1] = i;
        }
        Arrays.sort(b, Comparator.comparingInt(o -> o[0]));
        int y = 0;
        int x = m;
        int[] toReturn = new int[6];
        for (int i = 0; i < 6; i++){
            if(i + 1 == 6){y=x;}else{y=x/(6-(i));}
            if(b[i][0]-y < 0){
                x = x-b[i][0];
                toReturn[b[i][1]]=b[i][0];}
            else{
                x = x-y;
                toReturn[b[i][1]]=y;}
        }
        return toReturn;
    }


    public void handleStormlight(Level level, BlockPos pos, Direction dir){
        BlockEntity targetEntity = level.getBlockEntity(pos);
        if(targetEntity != null){
            int[] received = {0};
            targetEntity.getCapability(StormlightStorage.STORMLIGHT_STORAGE, dir).ifPresent(
                    handler -> {
                        if(handler.canReceive(dir)){
                            received[0] = handler.receiveStormlight(this.stormlightStorage.extractStormlight(10000, true), false);
                        }
                    }
            );
            this.stormlightStorage.extractStormlight(received[0], false);
        }
    }

    public static int getStormlightCapacityInDirection(Direction direction, BlockPos pos, Level level){
        BlockEntity targetEntity = level.getBlockEntity(pos.relative(direction));
        if(targetEntity == null){return 0;}
        int[] capacity = {0};
        targetEntity.getCapability(StormlightStorage.STORMLIGHT_STORAGE, direction.getOpposite()).ifPresent(
                handler -> {
                    if(handler.canReceive(direction.getOpposite())){
                    capacity[0] = handler.receiveStormlight(1000000000, true);
                    }
                }
        );
        return capacity[0];

    }

    public void pushStormlight(Direction direction, int stormlight){
        BlockEntity targetEntity = this.level.getBlockEntity(this.getBlockPos().relative(direction));
        if(targetEntity == null){return;}
        int[] received = new int[]{0};
        targetEntity.getCapability(StormlightStorage.STORMLIGHT_STORAGE, direction.getOpposite()).ifPresent(
                handler -> {
                    if(handler.canReceive(direction.getOpposite())){
                    received[0] = handler.receiveStormlight(this.stormlightStorage.extractStormlight(stormlight, true), false);
                    }
                }
        );
        this.stormlightStorage.extractStormlight(received[0], false);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, StormlightPipeBlockEntity pBlockEntity){
        int[] capacities = new int[6];
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_UP)){capacities[0] = getStormlightCapacityInDirection(Direction.UP, pPos, pLevel);}else{capacities[0] = 0;}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_DOWN)){capacities[1] = getStormlightCapacityInDirection(Direction.DOWN, pPos, pLevel);}else{capacities[1] = 0;}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_SOUTH)){capacities[2] = getStormlightCapacityInDirection(Direction.SOUTH, pPos, pLevel);}else{capacities[2] = 0;}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_NORTH)){capacities[3] = getStormlightCapacityInDirection(Direction.NORTH, pPos, pLevel);}else{capacities[3] = 0;}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_WEST)){capacities[4] = getStormlightCapacityInDirection(Direction.WEST, pPos, pLevel);}else{capacities[4] = 0;}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_EAST)){capacities[5] = getStormlightCapacityInDirection(Direction.EAST, pPos, pLevel);}else{capacities[5] = 0;}
        int[] toDistribute = getDistribution(pBlockEntity.stormlightStorage.getStormlightStored(), capacities);

        pBlockEntity.pushStormlight(Direction.UP, toDistribute[0]);
        pBlockEntity.pushStormlight(Direction.DOWN, toDistribute[1]);
        pBlockEntity.pushStormlight(Direction.SOUTH, toDistribute[2]);
        pBlockEntity.pushStormlight(Direction.NORTH, toDistribute[3]);
        pBlockEntity.pushStormlight(Direction.WEST, toDistribute[4]);
        pBlockEntity.pushStormlight(Direction.EAST, toDistribute[5]);

        /*
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_UP)){pBlockEntity.handleStormlight(pLevel, pPos.above(), Direction.UP.getOpposite());}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_DOWN)){pBlockEntity.handleStormlight(pLevel, pPos.below(), Direction.DOWN.getOpposite());}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_SOUTH)){pBlockEntity.handleStormlight(pLevel, pPos.south(), Direction.SOUTH.getOpposite());}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_NORTH)){pBlockEntity.handleStormlight(pLevel, pPos.north(), Direction.NORTH.getOpposite());}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_WEST)){pBlockEntity.handleStormlight(pLevel, pPos.west(), Direction.WEST.getOpposite());}
        if(!pState.getValue(StormlightPipeBlock.IS_INPUT_EAST)){pBlockEntity.handleStormlight(pLevel, pPos.east(), Direction.EAST.getOpposite());}
         */
    }
}