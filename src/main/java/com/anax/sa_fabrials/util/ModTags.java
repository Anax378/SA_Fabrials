package com.anax.sa_fabrials.util;

import com.anax.sa_fabrials.SAFabrials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(SAFabrials.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> EXPLOSION_SPREN_ATTRACTORS = tag("explosion_spren_attractors");
        public static final TagKey<Item> FIRE_SPREN_ATTRACTORS = tag("fire_spren_attractors");
        public static final TagKey<Item> LIGHTNING_SPREN_ATTRACTORS = tag("lightning_spren_attractors");

        public static final TagKey<Item> CAN_HOLD_SPREN = tag("can_hold_spren");

        public static final TagKey<Item> THROWABLE_FABRIALS = tag("throwable_fabrials");
        public static final TagKey<Item> REGULAR_FABRIALS = tag("regular_fabrials");

        public static final TagKey<Item> STEEL_INGOTS = forgeTag("ingot/steel");
        public static final TagKey<Item> IRON_INGOTS = forgeTag("ingot/iron");
        public static final TagKey<Item> ZINC_NUGGETS = forgeTag("nuggets/zinc");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(SAFabrials.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}