package com.anax.sa_fabrials.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TEST_MOD_TAB = new CreativeModeTab("sa_fabrials_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TOPAZ.get());
        }
    };
}
