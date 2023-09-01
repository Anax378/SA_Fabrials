package com.anax.sa_fabrials.util;

import com.anax.sa_fabrials.SAFabrials;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SATags {
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
        public static final TagKey<Item> WIND_SPREN_ATTRACTORS = tag("wind_spren_attractors");
        public static final TagKey<Item> HEALTH_SPREN_ATTRACTORS = tag("health_spren_attractors");
        public static final TagKey<Item> GRAVITY_SPREN_ATTRACTORS = tag("gravity_spren_attractors");
        public static final TagKey<Item> ICE_SPREN_ATTRACTORS = tag("ice_spren_attractors");
        public static final TagKey<Item> SMOKE_SPREN_ATTRACTORS = tag("smoke_spren_attractors");

        public static final TagKey<Item> CAN_HOLD_SPREN = tag("can_hold_spren");

        public static final TagKey<Item> STEEL_INGOTS = forgeTag("ingots/steel");
        public static final TagKey<Item> IRON_INGOTS = forgeTag("ingots/iron");
        public static final TagKey<Item> ZINC_NUGGETS = forgeTag("nuggets/zinc");

        public static boolean isHasItemTag(Item item, TagKey<Item> tag){
            return Registry.ITEM.getHolderOrThrow(Registry.ITEM.getResourceKey(item).get()).is(tag);
        }
        public static String getSpren(Item item){
            if(isHasItemTag(item, SATags.Items.EXPLOSION_SPREN_ATTRACTORS)){return "explosion";}
            if(isHasItemTag(item, SATags.Items.FIRE_SPREN_ATTRACTORS)){return "fire";}
            if(isHasItemTag(item, SATags.Items.LIGHTNING_SPREN_ATTRACTORS)){return "lightning";}
            if(isHasItemTag(item, SATags.Items.WIND_SPREN_ATTRACTORS)){return "wind";}
            if(isHasItemTag(item, SATags.Items.HEALTH_SPREN_ATTRACTORS)){return "health";}
            if(isHasItemTag(item, SATags.Items.GRAVITY_SPREN_ATTRACTORS)){return "gravity";}
            if(isHasItemTag(item, SATags.Items.ICE_SPREN_ATTRACTORS)){return "ice";}
            if(isHasItemTag(item, SATags.Items.SMOKE_SPREN_ATTRACTORS)){return "smoke";}
            return "none";
        };


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(SAFabrials.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}