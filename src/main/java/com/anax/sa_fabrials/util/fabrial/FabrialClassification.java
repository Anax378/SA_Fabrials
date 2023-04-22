package com.anax.sa_fabrials.util.fabrial;

import com.anax.sa_fabrials.item.ModItems;
import com.anax.sa_fabrials.item.custom.FabrialItem;
import com.anax.sa_fabrials.item.custom.GemstoneItem;
import com.anax.sa_fabrials.item.custom.ThrowableFabrialItem;
import net.minecraft.world.item.Item;

public class FabrialClassification {
    public static Item gem_from_throwable_fabrial(Item item){
        if(item == ModItems.THROWABLE_TOPAZ_FABRIAL.get()){return ModItems.TOPAZ_GEM.get();}
        if(item == ModItems.THROWABLE_SMOKESTONE_FABRIAL.get()){return ModItems.SMOKESTONE_GEM.get();}
        if(item == ModItems.THROWABLE_GARNET_FABRIAL.get()){return ModItems.GARNET_GEM.get();}
        if(item == ModItems.THROWABLE_ZIRCON_FABRIAL.get()){return ModItems.ZIRCON_GEM.get();}
        if(item == ModItems.THROWABLE_RUBY_FABRIAL.get()){return ModItems.RUBY_GEM.get();}
        if(item == ModItems.THROWABLE_SAPPHIRE_FABRIAL.get()){return ModItems.SAPPHIRE_GEM.get();}
        if(item == ModItems.THROWABLE_HELIODOR_FABRIAL.get()){return ModItems.HELIODOR_GEM.get();}
        if(item == ModItems.THROWABLE_DIAMOND_FABRIAL.get()){return ModItems.DIAMOND_GEM.get();}
        if(item == ModItems.THROWABLE_EMERALD_FABRIAL.get()){return ModItems.EMERALD_GEM.get();}
        if(item == ModItems.THROWABLE_AMETHYST_FABRIAL.get()){return ModItems.AMETHYST_GEM.get();}
        return null;
    }

    public static Item throwable_fabrial_from_gem(Item item){
        if(item == ModItems.TOPAZ_GEM.get()){return ModItems.THROWABLE_TOPAZ_FABRIAL.get();}
        if(item == ModItems.SMOKESTONE_GEM.get()){return ModItems.THROWABLE_SMOKESTONE_FABRIAL.get();}
        if(item == ModItems.GARNET_GEM.get()){return ModItems.THROWABLE_GARNET_FABRIAL.get();}
        if(item == ModItems.ZIRCON_GEM.get()){return ModItems.THROWABLE_ZIRCON_FABRIAL.get();}
        if(item == ModItems.RUBY_GEM.get()){return ModItems.THROWABLE_RUBY_FABRIAL.get();}
        if(item == ModItems.SAPPHIRE_GEM.get()){return ModItems.THROWABLE_SAPPHIRE_FABRIAL.get();}
        if(item == ModItems.HELIODOR_GEM.get()){return ModItems.THROWABLE_HELIODOR_FABRIAL.get();}
        if(item == ModItems.DIAMOND_GEM.get()){return ModItems.THROWABLE_DIAMOND_FABRIAL.get();}
        if(item == ModItems.EMERALD_GEM.get()){return ModItems.THROWABLE_EMERALD_FABRIAL.get();}
        if(item == ModItems.AMETHYST_GEM.get()){return ModItems.THROWABLE_AMETHYST_FABRIAL.get();}
        return null;
    }
    public static Item fabrial_from_gem(Item item){
        if(item == ModItems.TOPAZ_GEM.get()){return ModItems.TOPAZ_FABRIAL.get();}
        if(item == ModItems.SMOKESTONE_GEM.get()){return ModItems.SMOKESTONE_FABRIAL.get();}
        if(item == ModItems.GARNET_GEM.get()){return ModItems.GARNET_FABRIAL.get();}
        if(item == ModItems.ZIRCON_GEM.get()){return ModItems.ZIRCON_FABRIAL.get();}
        if(item == ModItems.RUBY_GEM.get()){return ModItems.RUBY_FABRIAL.get();}
        if(item == ModItems.SAPPHIRE_GEM.get()){return ModItems.SAPPHIRE_FABRIAL.get();}
        if(item == ModItems.HELIODOR_GEM.get()){return ModItems.HELIODOR_FABRIAL.get();}
        if(item == ModItems.DIAMOND_GEM.get()){return ModItems.DIAMOND_FABRIAL.get();}
        if(item == ModItems.EMERALD_GEM.get()){return ModItems.EMERALD_FABRIAL.get();}
        if(item == ModItems.AMETHYST_GEM.get()){return ModItems.AMETHYST_FABRIAL.get();}
        return null;
    }

    public static Item gem_from_fabrial(Item item){
        if(item == ModItems.TOPAZ_FABRIAL.get()){return ModItems.TOPAZ_GEM.get();}
        if(item == ModItems.SMOKESTONE_FABRIAL.get()){return ModItems.SMOKESTONE_GEM.get();}
        if(item == ModItems.GARNET_FABRIAL.get()){return ModItems.GARNET_GEM.get();}
        if(item == ModItems.ZIRCON_FABRIAL.get()){return ModItems.ZIRCON_GEM.get();}
        if(item == ModItems.RUBY_FABRIAL.get()){return ModItems.RUBY_GEM.get();}
        if(item == ModItems.SAPPHIRE_FABRIAL.get()){return ModItems.SAPPHIRE_GEM.get();}
        if(item == ModItems.HELIODOR_FABRIAL.get()){return ModItems.HELIODOR_GEM.get();}
        if(item == ModItems.DIAMOND_FABRIAL.get()){return ModItems.DIAMOND_GEM.get();}
        if(item == ModItems.EMERALD_FABRIAL.get()){return ModItems.EMERALD_GEM.get();}
        if(item == ModItems.AMETHYST_FABRIAL.get()){return ModItems.AMETHYST_GEM.get();}
        return null;
    }


}
