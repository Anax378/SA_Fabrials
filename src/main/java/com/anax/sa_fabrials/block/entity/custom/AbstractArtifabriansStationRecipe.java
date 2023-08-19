package com.anax.sa_fabrials.block.entity.custom;

import net.minecraft.world.item.ItemStack;

public abstract class AbstractArtifabriansStationRecipe {
    public boolean isValidRecipe(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom){return false;}
    public ItemStack constructMiddle(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom){return null;}
    public void consumeIngredients(ItemStack top, ItemStack left, ItemStack right, ItemStack bottom){return;}
    public boolean canDeconstruct(ItemStack middle){return false;}
    public ItemStack[] deconstruct(ItemStack middle){return null;}
}
