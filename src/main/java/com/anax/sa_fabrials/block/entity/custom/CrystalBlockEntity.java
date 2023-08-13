package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.screen.CrystalMenu;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class CrystalBlockEntity extends BlockEntity implements MenuProvider {
    BlockEntityType<?> blockEntityType;

    protected final ContainerData data;
    int maxExtract;
    int maxReceive;
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
        }
    };

    public final StormlightStorage stormlightStorage;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<StormlightStorage> lazyStormlightStorage = LazyOptional.empty();

    public CrystalBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int maxReceive, int maxExtract, int capacity) {
        super(blockEntityType, blockPos, blockState);
        this.blockEntityType = blockEntityType;
        this.stormlightStorage = new StormlightStorage(capacity, maxReceive, maxExtract, 0) {
            @Override
            public void onChanged() {
                setChanged();
                level.getLightEngine().checkBlock(blockPos);
            }

            @Override
            public boolean canExtract(@Nullable Direction direction) {
                if(direction != null)
                {if(direction != getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)){return false;}}
                return super.canExtract(direction);
            }

            @Override
            public boolean canReceive(@Nullable Direction direction) {
                if(direction != null)
                {if(direction != getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite()){return false;}}
                return super.canReceive(direction);
            }
        };
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                switch(index){
                    case 0: return CrystalBlockEntity.this.stormlightStorage.getStormlightStored();
                    case 1: return CrystalBlockEntity.this.stormlightStorage.getMaxStormlightStored();
                }
                return 0;
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int getCount() {
                return 2;
            }
        };
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    @NotNull
    public BlockEntityType<?> getType(){
        return blockEntityType;
    }



    @Override
    public Component getDisplayName() {
        return Component.literal("Crystal");
    }

    abstract Block getUserBlock();
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new CrystalMenu(pContainerId, pInventory, this, getUserBlock(), data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        if(cap == StormlightStorage.STORMLIGHT_STORAGE){
            if(side == null){return lazyStormlightStorage.cast();}
            if(side.equals(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) || side.equals(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite())){
                return lazyStormlightStorage.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyStormlightStorage = LazyOptional.of(() -> stormlightStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyStormlightStorage.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.merge(stormlightStorage.saveOnNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        stormlightStorage.loadFromNBT(nbt);
    }
    abstract Item getItem();

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()+1);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack stack = getItem().getDefaultInstance();
        stack.getOrCreateTag().putInt("stormlight", this.stormlightStorage.getStormlightStored());
        inventory.setItem(itemHandler.getSlots(), stack);
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CrystalBlockEntity pBlockEntity) {
        final int[] received = {0};
        pBlockEntity.itemHandler.getStackInSlot(1).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                handler -> {
                    if(handler.canReceive(null)){
                    received[0] = handler.receiveStormlight(pBlockEntity.stormlightStorage.extractStormlight(pBlockEntity.maxExtract, true), false);}
                }
        );
        pBlockEntity.stormlightStorage.extractStormlight(received[0], false);

        final int[] extracted = {0};
        pBlockEntity.itemHandler.getStackInSlot(0).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                handler -> {
                    if (handler.canExtract(null)){
                    extracted[0] = handler.extractStormlight(pBlockEntity.stormlightStorage.receiveStormlight(pBlockEntity.maxReceive, true), false);}}
        );
        pBlockEntity.stormlightStorage.receiveStormlight(extracted[0], false);

        Direction facing = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        received[0] = 0;
        BlockEntity entity = pLevel.getBlockEntity(pPos.relative(facing));
        if(entity != null){
        entity.getCapability(StormlightStorage.STORMLIGHT_STORAGE, facing.getOpposite()).ifPresent(
                handler -> {
                    if(handler.canReceive(facing.getOpposite())){
                        received[0] = handler.receiveStormlight(pBlockEntity.stormlightStorage.extractStormlight(pBlockEntity.maxExtract, true), false);
                    }
                }
        );
        pBlockEntity.stormlightStorage.extractStormlight(received[0], false);
        }
    }
}
