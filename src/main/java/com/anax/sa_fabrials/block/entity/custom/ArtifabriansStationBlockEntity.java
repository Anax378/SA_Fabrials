package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.screen.ArtifabriansStationMenu;
import com.anax.sa_fabrials.item.SAItems;
import com.anax.sa_fabrials.item.custom.AbstractFabrialItem;
import com.anax.sa_fabrials.item.custom.FabrialItem;
import com.anax.sa_fabrials.item.custom.GemstoneItem;
import com.anax.sa_fabrials.item.custom.ThrowableFabrialItem;
import com.anax.sa_fabrials.util.ModTags;
import com.anax.sa_fabrials.util.fabrial.FabrialClassification;
import leaf.cosmere.api.Metals;
import leaf.cosmere.common.registry.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArtifabriansStationBlockEntity extends BlockEntity implements MenuProvider {
    private boolean isHasCraftedItem = false;
    static int topSlot = 0;
    static int leftSlot = 1;
    static int middleSlot = 2;
    static int rightSlot = 3;
    static int bottomSlot = 4;

    public ItemStackHandler itemStackHandler = new ItemStackHandler(5){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(slot == 2 && !areOuterSlotsEmpty()){return false;}
            return super.isItemValid(slot, stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            System.out.println("content changed");
            AbstractArtifabriansStationRecipe recipe;
            if(slot == 2 && isHasCraftedItem && itemStackHandler.getStackInSlot(slot).isEmpty() &&
                    (recipe = ArtifabriansStationRecipes.getConstructionRecipe(
                            itemStackHandler.getStackInSlot(topSlot),
                            itemStackHandler.getStackInSlot(leftSlot),
                            itemStackHandler.getStackInSlot(rightSlot),
                            itemStackHandler.getStackInSlot(bottomSlot))
                    ) != null){

                recipe.consumeIngredients(
                        itemStackHandler.getStackInSlot(topSlot),
                        itemStackHandler.getStackInSlot(leftSlot),
                        itemStackHandler.getStackInSlot(rightSlot),
                        itemStackHandler.getStackInSlot(bottomSlot)
                );
                isHasCraftedItem = false;
                System.out.println("consumed ingredients");
            }
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
        return Component.translatable("menu.sa_fabrials.artifabrians_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ArtifabriansStationMenu(containerId, inventory, this);
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
        AbstractArtifabriansStationRecipe recipe;
        if(isHasCraftedItem && (
                recipe = ArtifabriansStationRecipes.getConstructionRecipe(
                itemStackHandler.getStackInSlot(topSlot),
                itemStackHandler.getStackInSlot(leftSlot),
                itemStackHandler.getStackInSlot(rightSlot),
                itemStackHandler.getStackInSlot(bottomSlot))
        ) != null){
            recipe.consumeIngredients(
                    itemStackHandler.getStackInSlot(topSlot),
                    itemStackHandler.getStackInSlot(leftSlot),
                    itemStackHandler.getStackInSlot(rightSlot),
                    itemStackHandler.getStackInSlot(bottomSlot)
            );
            isHasCraftedItem = false;
        }
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
        if(ForgeRegistries.ITEMS.tags() == null){return false;}
        return ForgeRegistries.ITEMS.tags().getTag(tag).contains(item);
    }

    public boolean areOuterSlotsEmpty(){
        return
                itemStackHandler.getStackInSlot(topSlot).isEmpty() &&
                itemStackHandler.getStackInSlot(leftSlot).isEmpty() &&
                itemStackHandler.getStackInSlot(rightSlot).isEmpty() &&
                itemStackHandler.getStackInSlot(bottomSlot).isEmpty();
    }

    public void updateContents(){
        if(areOuterSlotsEmpty()) {
            AbstractArtifabriansStationRecipe recipe = ArtifabriansStationRecipes.getDeconstructionRecipe(itemStackHandler.getStackInSlot(2));
            if(recipe != null){
                System.out.println("recipe exists");
                ItemStack[] ingredients = recipe.deconstruct(itemStackHandler.getStackInSlot(middleSlot));
                itemStackHandler.setStackInSlot(topSlot, ingredients[0]);
                itemStackHandler.setStackInSlot(leftSlot, ingredients[1]);
                itemStackHandler.setStackInSlot(rightSlot, ingredients[2]);
                itemStackHandler.setStackInSlot(bottomSlot, ingredients[3]);
                isHasCraftedItem = false;
                itemStackHandler.setStackInSlot(middleSlot, ItemStack.EMPTY);
            }
        }
        AbstractArtifabriansStationRecipe recipe;
        if(
                (recipe = ArtifabriansStationRecipes.getConstructionRecipe(
                itemStackHandler.getStackInSlot(topSlot),
                itemStackHandler.getStackInSlot(leftSlot),
                itemStackHandler.getStackInSlot(rightSlot),
                itemStackHandler.getStackInSlot(bottomSlot))) != null ){

            System.out.println("created middle slot");
            ItemStack middle = recipe.constructMiddle(itemStackHandler.getStackInSlot(topSlot), itemStackHandler.getStackInSlot(leftSlot), itemStackHandler.getStackInSlot(rightSlot), itemStackHandler.getStackInSlot(bottomSlot));
            itemStackHandler.setStackInSlot(middleSlot, middle);
            isHasCraftedItem = true;

        }else{
            itemStackHandler.setStackInSlot(middleSlot, ItemStack.EMPTY);
            isHasCraftedItem = false;
        }
        return;
/*
        if(itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(1).isEmpty() && itemStackHandler.getStackInSlot(3).isEmpty() && itemStackHandler.getStackInSlot(4).isEmpty()) {
            if (itemStackHandler.getStackInSlot(2).getItem() instanceof AbstractFabrialItem) {
                ItemStack gemItemStack = null;

                if (itemStackHandler.getStackInSlot(2).getItem() instanceof ThrowableFabrialItem && FabrialClassification.gem_from_throwable_fabrial(itemStackHandler.getStackInSlot(2).getItem()) != null) {
                    itemStackHandler.setStackInSlot(3, SAItems.THROWABLE_FABRIAL_CASING.get().getDefaultInstance());
                    gemItemStack = FabrialClassification.gem_from_throwable_fabrial(itemStackHandler.getStackInSlot(2).getItem()).getDefaultInstance();
                }

                if (itemStackHandler.getStackInSlot(2).getItem() instanceof FabrialItem && FabrialClassification.gem_from_fabrial(itemStackHandler.getStackInSlot(2).getItem()) != null) {
                    itemStackHandler.setStackInSlot(3, SAItems.FABRIAL_CASING.get().getDefaultInstance());
                    gemItemStack = FabrialClassification.gem_from_fabrial(itemStackHandler.getStackInSlot(2).getItem()).getDefaultInstance();
                }


                if(gemItemStack != null) {
                    itemStackHandler.setStackInSlot(1, itemStackHandler.getStackInSlot(2).getOrCreateTag().getBoolean("is_attractor")
                            ? Items.IRON_INGOT.getDefaultInstance() : ItemsRegistry.METAL_INGOTS.get(Metals.MetalType.STEEL).get().getDefaultInstance());
                    itemStackHandler.setStackInSlot(4, ItemsRegistry.METAL_NUGGETS.get(Metals.MetalType.ZINC).get().getDefaultInstance());
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

            if(itemStackHandler.getStackInSlot(3).is(SAItems.THROWABLE_FABRIAL_CASING.get())){
                resultItemStack = FabrialClassification.throwable_fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()).getDefaultInstance();
            }

            if(itemStackHandler.getStackInSlot(3).is(SAItems.FABRIAL_CASING.get())){
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

*/
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
                && (itemStackHandler.getStackInSlot(3).is(SAItems.THROWABLE_FABRIAL_CASING.get()) || itemStackHandler.getStackInSlot(3).is(SAItems.FABRIAL_CASING.get()))
                && isHasItemTag(itemStackHandler.getStackInSlot(4).getItem(), ModTags.Items.ZINC_NUGGETS)
                && (FabrialClassification.throwable_fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()) != null || FabrialClassification.fabrial_from_gem(itemStackHandler.getStackInSlot(0).getItem()) != null)
        );
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ArtifabriansStationBlockEntity pBlockEntity){
        pBlockEntity.updateContents();
    }
}
