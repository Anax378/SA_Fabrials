package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.screen.SprenCatchingStationMenu;
import com.anax.sa_fabrials.util.ModTags;
import com.anax.sa_fabrials.util.fabrial.GemCapacities;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SprenCatchingStationBlockEntity extends BlockEntity implements MenuProvider {
    private boolean isCrafting = false;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            isCrafting = false;
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public SprenCatchingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SPREN_CATCHING_STATION_ENTITY.get(), blockPos, blockState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        lazyItemHandler.invalidate();
        super.invalidateCaps();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Spren Catching Station");
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.SPREN_CATCHING_STATION_ENTITY.get();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new SprenCatchingStationMenu(containerId, inventory, this);
    }

    public static boolean isHasItemTag(Item item, TagKey<Item> tag){
        return Registry.ITEM.getHolderOrThrow(Registry.ITEM.getResourceKey(item).get()).is(tag);
    }

    @Nullable
    public static String getAssociatedSpren(Item item){
        if(isHasItemTag(item, ModTags.Items.EXPLOSION_SPREN_ATTRACTORS)){return "explosion";}
        if(isHasItemTag(item, ModTags.Items.FIRE_SPREN_ATTRACTORS)){return "fire";}
        if(isHasItemTag(item, ModTags.Items.LIGHTNING_SPREN_ATTRACTORS)){return "lightning";}
        if(isHasItemTag(item, ModTags.Items.WIND_SPREN_ATTRACTORS)){return "wind";}
        if(isHasItemTag(item, ModTags.Items.HEALTH_SPREN_ATTRACTORS)){return "health";}
        return null;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SprenCatchingStationBlockEntity pBlockEntity) {
        if(pBlockEntity.isCrafting){
            pBlockEntity.itemStackHandler.getStackInSlot(1).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                    handler -> {
                        int[] extracted = {0};
                        pBlockEntity.itemStackHandler.getStackInSlot(2).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                                sHandler -> {
                                    extracted[0] = sHandler.receiveStormlight(handler.extractStormlight(100000, true), false);
                                }
                        );
                        handler.extractStormlight(extracted[0], false);
                    }
            );

            boolean[] isFinishedCrafting = {false};
            pBlockEntity.itemStackHandler.getStackInSlot(1).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                    handler -> {
                        isFinishedCrafting[0] = handler.getStormlightStored() == 0;
                    }
            );

            if(isFinishedCrafting[0] && isHasItemTag(pBlockEntity.itemStackHandler.getStackInSlot(1).getItem(), ModTags.Items.CAN_HOLD_SPREN)){
                if(getAssociatedSpren(pBlockEntity.itemStackHandler.getStackInSlot(0).getItem()) != null) {
                    pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1, 1);
                    pBlockEntity.itemStackHandler.getStackInSlot(1).getOrCreateTag().putString("spren", getAssociatedSpren(pBlockEntity.itemStackHandler.getStackInSlot(0).getItem()));
                }
                pBlockEntity.isCrafting = false;
            }
        }
        else {
            if (getAssociatedSpren(pBlockEntity.itemStackHandler.getStackInSlot(0).getItem()) != null && pBlockEntity.itemStackHandler.getStackInSlot(1).getCapability(StormlightStorage.STORMLIGHT_STORAGE).isPresent() && pBlockEntity.itemStackHandler.getStackInSlot(2).getCapability(StormlightStorage.STORMLIGHT_STORAGE).isPresent()) {
                int[] capacities = {0, 0};
                boolean[] data = {false, false};
                pBlockEntity.itemStackHandler.getStackInSlot(1).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                        handler -> {
                            data[0] = (handler.getStormlightStored() == handler.getMaxStormlightStored());
                            capacities[0] = handler.getMaxStormlightStored();
                        }
                );
                pBlockEntity.itemStackHandler.getStackInSlot(2).getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(
                        handler -> {
                            data[1] = (handler.getStormlightStored() == 0);
                            capacities[1] = handler.getMaxStormlightStored();
                        }
                );
                if (data[0] && data[1] && (capacities[0] <= capacities[1] && isHasItemTag(pBlockEntity.itemStackHandler.getStackInSlot(1).getItem(), ModTags.Items.CAN_HOLD_SPREN))) {
                    pBlockEntity.isCrafting = true;
                    pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1, 1);
                }

            }
        }
    }
}
