package com.anax.sa_fabrials.block.entity.custom;

import com.anax.sa_fabrials.item.SAItems;
import com.anax.sa_fabrials.item.custom.PulseFabrialItem;
import com.anax.sa_fabrials.item.custom.GemstoneItem;
import com.anax.sa_fabrials.item.custom.ThrowableFabrialItem;
import com.anax.sa_fabrials.util.ModTags;
import com.anax.sa_fabrials.util.fabrial.FabrialClassification;
import leaf.cosmere.api.Metals;
import leaf.cosmere.common.registry.ItemsRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ArtifabriansStationRecipes {
    public static boolean isHasItemTag(Item item, TagKey<Item> tag){
        if(ForgeRegistries.ITEMS.tags() == null){return false;}
        return ForgeRegistries.ITEMS.tags().getTag(tag).contains(item);
    }

    public static AbstractArtifabriansStationRecipe[] recipes = new AbstractArtifabriansStationRecipe[]{
            new AbstractArtifabriansStationRecipe() {
            @Override
            public boolean isValidRecipe(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom) {
                if(top.getItem() instanceof GemstoneItem){}else{return false;}
                if(isHasItemTag(left.getItem(), ModTags.Items.IRON_INGOTS) || isHasItemTag(left.getItem(), ModTags.Items.STEEL_INGOTS)){}else{return false;}
                if(right.is(SAItems.FABRIAL_CASING.get()) || right.is(SAItems.THROWABLE_FABRIAL_CASING.get())){}else{return false;}
                if(isHasItemTag(bottom.getItem(), ModTags.Items.ZINC_NUGGETS)){}else{return false;}
                return true;
            }
            @Override
            public boolean canDeconstruct(ItemStack middle) {
                if(middle.getItem() instanceof ThrowableFabrialItem && FabrialClassification.gem_from_throwable_fabrial(middle.getItem()) != null){return true;}
                if(middle.getItem() instanceof PulseFabrialItem && FabrialClassification.gem_from_fabrial(middle.getItem()) != null){return true;}
                return false;
            }

            @Override
            @Nullable
            public ItemStack[] deconstruct(ItemStack middle) {
                if(!this.canDeconstruct(middle)){return null;}
                ItemStack top = middle.getItem() instanceof PulseFabrialItem ? FabrialClassification.gem_from_fabrial(middle.getItem()).getDefaultInstance() : FabrialClassification.gem_from_throwable_fabrial(middle.getItem()).getDefaultInstance();
                top.getOrCreateTag().putString("spren", middle.getOrCreateTag().getString("spren"));
                top.getOrCreateTag().putInt("stormlight", middle.getOrCreateTag().getInt("stormlight"));
                ItemStack left = middle.getOrCreateTag().getBoolean("is_attractor") ? Items.IRON_INGOT.getDefaultInstance() : ItemsRegistry.METAL_INGOTS.get(Metals.MetalType.STEEL).get().getDefaultInstance();
                ItemStack right = middle.getItem() instanceof PulseFabrialItem ? SAItems.FABRIAL_CASING.get().getDefaultInstance() : SAItems.THROWABLE_FABRIAL_CASING.get().getDefaultInstance();
                ItemStack bottom = ItemsRegistry.METAL_NUGGETS.get(Metals.MetalType.ZINC).get().getDefaultInstance();
                bottom.setCount(middle.getOrCreateTag().getInt("power"));
                return new ItemStack[]{top, left, right, bottom};
            }

            @Override
            public ItemStack constructMiddle(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom) {
                if(isValidRecipe(top, left, right, bottom)){}else{return null;}
                ItemStack middle = right.is(SAItems.FABRIAL_CASING.get()) ? FabrialClassification.fabrial_from_gem(top.getItem()).getDefaultInstance() : FabrialClassification.throwable_fabrial_from_gem(top.getItem()).getDefaultInstance();
                middle.getOrCreateTag().putInt("stormlight", top.getOrCreateTag().getInt("stormlight"));
                middle.getOrCreateTag().putString("spren", top.getOrCreateTag().getString("spren"));
                middle.getOrCreateTag().putInt("power", Math.min(bottom.getCount(), 5));
                middle.getOrCreateTag().putBoolean("is_attractor", isHasItemTag(left.getItem(), ModTags.Items.IRON_INGOTS));
                return middle;
            }

            @Override
            public void consumeIngredients(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom) {
                top.shrink(1);
                left.shrink(1);
                right.shrink(1);
                bottom.shrink(5);
            }
        }
    };

    @Nullable
    public static AbstractArtifabriansStationRecipe getDeconstructionRecipe(ItemStack middle){
        for(int i = 0; i < recipes.length; i++){
            if(recipes[i].canDeconstruct(middle)){
                return recipes[i];
            }
        }
        return null;
    }
    @Nullable
    public static AbstractArtifabriansStationRecipe getConstructionRecipe(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom) {
        for(int i = 0; i < recipes.length; i++){
            if(recipes[i].isValidRecipe(top, left, right, bottom)){
                return recipes[i];
            }
        }
        return null;
    }

}
