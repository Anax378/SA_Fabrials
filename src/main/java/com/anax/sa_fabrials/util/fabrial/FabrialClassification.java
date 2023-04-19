package com.anax.sa_fabrials.util.fabrial;

import com.anax.sa_fabrials.item.ModItems;
import com.anax.sa_fabrials.item.custom.FabrialItem;
import com.anax.sa_fabrials.item.custom.GemstoneItem;
import com.anax.sa_fabrials.item.custom.ThrowableFabrialItem;
import net.minecraft.world.item.Item;

public class FabrialClassification {
    public static final int TOPAZ_GEM_CAPACITY = 64000;
    public static final int SMOKESTONE_GEM_CAPACITY = 1000;
    public static final int GARNET_GEM_CAPACITY = 8000;
    public static final int ZIRCON_GEM_CAPACITY = 12000;
    public static final int RUBY_GEM_CAPACITY = 20000;
    public static final int SAPPHIRE_GEM_CAPACITY = 28000;
    public static final int HELIODOR_GEM_CAPACITY = 16000;
    public static final int DIAMOND_GEM_CAPACITY = 32000;
    public static final int EMERALD_GEM_CAPACITY = 4000;
    public static final int AMETHYST_GEM_CAPACITY = 2000;

    public static Item gem_from_throwable_fabrial(ThrowableFabrialItem item){
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

    public static Item throwable_fabrial_from_gem(GemstoneItem item){
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

}
