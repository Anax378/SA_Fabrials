package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.screen.ArtifabriansStationMenu;
import com.anax.sa_fabrials.item.ModItems;
import com.anax.sa_fabrials.item.custom.AbstractFabrialItem;
import com.anax.sa_fabrials.item.custom.FabrialItem;
import com.anax.sa_fabrials.item.custom.GemstoneItem;
import com.anax.sa_fabrials.item.custom.ThrowableFabrialItem;
import com.anax.sa_fabrials.util.ModTags;
import com.anax.sa_fabrials.util.fabrial.FabrialClassification;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArtifabriansStationBlockEntity extends BlockEntity implements MenuProvider {
    private boolean isHasCraftedItem = false;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(5){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(slot == 2){
                if(itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(1).isEmpty() && itemStackHandler.getStackInSlot(3).isEmpty() && itemStackHandler.getStackInSlot(4).isEmpty()  &&  ((FabrialClassification.gem_from_fabrial(stack.getItem()) != null) || (FabrialClassification.gem_from_throwable_fabrial(stack.getItem()) != null))) {
                    return true;
                }else return false;
            }
            return super.isItemValid(slot, stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            if(slot == 2 && isHasCraftedItem && itemStackHandler.getStackInSlot(slot).isEmpty()){consume_ingredients();}
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ArtifabriansStationBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public ArtifabriansStationBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.ARTIFABRIANS_STATION_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("menu.sa_fabrials.artifabrians_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ArtifabriansStationMenu(containerId, inventory, this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        lazyItemHandler.invalidate();
        super.invalidateCaps();
    }

    @Override
    public void onLoad() {
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
        super.onLoad();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
        super.load(tag);
    }
    public void drops(){
        if(isHasCraftedItem){consume_ingredients();isHasCraftedItem = false;}
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.ARTIFABRIANS_STATION_BLOCK_ENTITY.get();
    }

    public static boolean isHasItemTag(Item item, TagKey<Item> tag){
        return Registry.ITEM.getHolderOrThrow(Registry.ITEM.getResourceKey(item).get()).is(tag);
    }

    public void updateContents(){
        if(itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(1).isEmpty() && itemStackHandler.getStackInSlot(3).isEmpty() && itemStackHandler.getStackInSlot(4).isEmpty()) {
            if (itemStackHandler.getStackInSlot(2).getItem() instanceof AbstractFabrialItem) {
                ItemStack gemItemStack = null;

                if (itemStackHandler.getStackInSlot(2).getItem() instanceof ThrowableFabrialItem && FabrialClassification.gem_from_throwable_fabrial(itemStackHandler.getStackInSlot(2).getItem()) != null) {
                    itemStackHandler.setStackInSlot(3, ModItems.THROWABLE_FABRIAL_CASING.get().getDefaultInstance());
                    gemItemStack = FabrialClassification.gem_from_throwable_fabrial(itemStackHandler.getStackInSlot(2).getItem()).getDefaultInstance();
                }

                if (itemStackHandler.getStackInSlot(2).getItem() instanceof FabrialItem && FabrialClassification.gem_from_fabrial(itemStackHandler.getStackInSlot(2).getItem()) != null) {
                    itemStackHandler.setStackInSlot(3, ModItems.FABRIAL_CASING.get().getDefaultInstance());
                    gemItemStack = FabrialClassification.gem_from_fabrial(itemStackHandler.getStackInSlot(2).getItem()).getDefaultInstance();
                }


                if(gemItemStack != null) {
                    itemStackHandler.setStackInSlot(1, itemStackHandler.getStackInSlot(2).getOrCreateTag().getBoolean("is_attractor")
                            ? Items.IRON_INGOT.getDefaultInstance() : ModItems.STEEL_INGOT.get().getDefaultInstance());
                    itemStackHandler.setStackInSlot(4, ModItems.ZINC_NUGGET.get().getDefaultInstance());
                    itemStackHandler.getStackInSlot(4).setCount(itemStackHandler.getStackInSlot(2).getOrCreateTag().getInt("power"));

                    gemItemStack.getOrCreateTag().putString("spren", itemStackHandler.getStackInSlot(2).getOrCreateTag().getString("spren"));
                    gemItemStack.getOrCreateTag().putInt("stormlight_capacity", itemStackHandler.getStackInSlot(2).getOrCreateTag().getInt("stormlight_capacity"));
                    gemItemStack.getOrCreateTag().putInt("stormlight_maxReceive", itemStackHandler.getStackInSlot(2).getOrCreateTag().getInt("stormlight_maxReceive"));
                    gemItemStack.getOrCreateTag().putInt("stormlight_maxExtract", itemStackHandler.getStackInSlot(2).getOrCreateTag().getInt("stormlight_maxExtract"));
                    gemItemStack.getOrCreateTag().putInt("stormlight", itemStackHandler.getStackInSlot(2).getOrCreateTag().getInt("stormlight"));
                    itemStackHandler.setStackInSlot(0, gemItemStack);
                }
                itemStackHandler.setStackInSlot(2, ItemStack.EMPTY);
            }
        }

        if(has_valid_recipe()){
            ItemStack resultItemStack = null;

            if(itemStackHandler.getStackInSlot(3).is(ModItems.THROWABLE_FABRIAL_CASING.get())){
                resultItemStack = FabrialClassification.throwable_fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()).getDefaultInstance();
            }

            if(itemStackHandler.getStackInSlot(3).is(ModItems.FABRIAL_CASING.get())){
                resultItemStack = FabrialClassification.fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()).getDefaultInstance();
            }

            if(resultItemStack != null) {
                resultItemStack.getOrCreateTag().putString("spren", itemStackHandler.getStackInSlot(0).getOrCreateTag().getString("spren"));
                resultItemStack.getOrCreateTag().putInt("stormlight_capacity", itemStackHandler.getStackInSlot(0).getOrCreateTag().getInt("stormlight_capacity"));
                resultItemStack.getOrCreateTag().putInt("stormlight_maxReceive", itemStackHandler.getStackInSlot(0).getOrCreateTag().getInt("stormlight_maxReceive"));
                resultItemStack.getOrCreateTag().putInt("stormlight_maxExtract", itemStackHandler.getStackInSlot(0).getOrCreateTag().getInt("stormlight_maxExtract"));
                resultItemStack.getOrCreateTag().putInt("stormlight", itemStackHandler.getStackInSlot(0).getOrCreateTag().getInt("stormlight"));

                resultItemStack.getOrCreateTag().putBoolean("is_attractor", isHasItemTag(itemStackHandler.getStackInSlot(1).getItem(), ModTags.Items.IRON_INGOTS));
                resultItemStack.getOrCreateTag().putInt("power", Math.min(itemStackHandler.getStackInSlot(4).getCount(), 5));
                itemStackHandler.setStackInSlot(2, resultItemStack);
                isHasCraftedItem = true;
            }else{
                isHasCraftedItem = false;
                itemStackHandler.setStackInSlot(2, ItemStack.EMPTY);
            }
        }else{
            isHasCraftedItem = false;
            itemStackHandler.setStackInSlot(2, ItemStack.EMPTY);
        }
    }
    void consume_ingredients(){
        itemStackHandler.getStackInSlot(0).shrink(1);
        itemStackHandler.getStackInSlot(1).shrink(1);
        itemStackHandler.getStackInSlot(3).shrink(1);
        itemStackHandler.getStackInSlot(4).shrink(5);
    }

    boolean has_valid_recipe(){
        return (itemStackHandler.getStackInSlot(0).getItem() instanceof GemstoneItem
                && (isHasItemTag(itemStackHandler.getStackInSlot(1).getItem(), ModTags.Items.STEEL_INGOTS) || isHasItemTag(itemStackHandler.getStackInSlot(1).getItem(), ModTags.Items.IRON_INGOTS))
                && (itemStackHandler.getStackInSlot(3).is(ModItems.THROWABLE_FABRIAL_CASING.get()) || itemStackHandler.getStackInSlot(3).is(ModItems.FABRIAL_CASING.get()))
                && isHasItemTag(itemStackHandler.getStackInSlot(4).getItem(), ModTags.Items.ZINC_NUGGETS)
                && (FabrialClassification.throwable_fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()) != null || FabrialClassification.fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()) != null)
        );
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ArtifabriansStationBlockEntity pBlockEntity){
        pBlockEntity.updateContents();
    }
}
