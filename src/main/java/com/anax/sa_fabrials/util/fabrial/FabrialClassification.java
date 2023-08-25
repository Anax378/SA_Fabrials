package com.anax.sa_fabrials.util.fabrial;

import com.anax.sa_fabrials.item.SAItems;
import leaf.cosmere.api.IHasGemType;
import net.minecraft.world.item.Item;

public class FabrialClassification {

    public static Item gemFromType(GemType type){
        if(type == TOPAZ){return SAItems.TOPAZ_GEM.get();}
        if(type == SMOKESTONE){return SAItems.SMOKESTONE_GEM.get();}
        if(type == GARNET){return SAItems.GARNET_GEM.get();}
        if(type == ZIRCON){return SAItems.ZIRCON_GEM.get();}
        if(type == RUBY){return SAItems.RUBY_GEM.get();}
        if(type == SAPPHIRE){return SAItems.SAPPHIRE_GEM.get();}
        if(type == HELIODOR){return SAItems.HELIODOR_GEM.get();}
        if(type == DIAMOND){return SAItems.DIAMOND_GEM.get();}
        if(type == EMERALD){return SAItems.EMERALD_GEM.get();}
        if(type == AMETHYST){return SAItems.AMETHYST_GEM.get();}
        return null;
    }


    public static Item pulseFabrialFromType(GemType type){
        if(type == TOPAZ){return SAItems.TOPAZ_FABRIAL.get();}
        if(type == SMOKESTONE){return SAItems.SMOKESTONE_FABRIAL.get();}
        if(type == GARNET){return SAItems.GARNET_FABRIAL.get();}
        if(type == ZIRCON){return SAItems.ZIRCON_FABRIAL.get();}
        if(type == RUBY){return SAItems.RUBY_FABRIAL.get();}
        if(type == SAPPHIRE){return SAItems.SAPPHIRE_FABRIAL.get();}
        if(type == HELIODOR){return SAItems.HELIODOR_FABRIAL.get();}
        if(type == DIAMOND){return SAItems.DIAMOND_FABRIAL.get();}
        if(type == EMERALD){return SAItems.EMERALD_FABRIAL.get();}
        if(type == AMETHYST){return SAItems.AMETHYST_FABRIAL.get();}
        return null;
    }

    public static Item throwableFabrialFromType(GemType type){
        if(type == TOPAZ){return SAItems.THROWABLE_TOPAZ_FABRIAL.get();}
        if(type == SMOKESTONE){return SAItems.THROWABLE_SMOKESTONE_FABRIAL.get();}
        if(type == GARNET){return SAItems.THROWABLE_GARNET_FABRIAL.get();}
        if(type == ZIRCON){return SAItems.THROWABLE_ZIRCON_FABRIAL.get();}
        if(type == RUBY){return SAItems.THROWABLE_RUBY_FABRIAL.get();}
        if(type == SAPPHIRE){return SAItems.THROWABLE_SAPPHIRE_FABRIAL.get();}
        if(type == HELIODOR){return SAItems.THROWABLE_HELIODOR_FABRIAL.get();}
        if(type == DIAMOND){return SAItems.THROWABLE_DIAMOND_FABRIAL.get();}
        if(type == EMERALD){return SAItems.THROWABLE_EMERALD_FABRIAL.get();}
        if(type == AMETHYST){return SAItems.THROWABLE_AMETHYST_FABRIAL.get();}
        return null;
    }

    public static Item toggleFabrialFromType(GemType type){
        if(type == TOPAZ){return SAItems.TOGGLE_TOPAZ_FABRIAL.get();}
        if(type == SMOKESTONE){return SAItems.TOGGLE_SMOKESTONE_FABRIAL.get();}
        if(type == GARNET){return SAItems.TOGGLE_GARNET_FABRIAL.get();}
        if(type == ZIRCON){return SAItems.TOGGLE_ZIRCON_FABRIAL.get();}
        if(type == RUBY){return SAItems.TOGGLE_RUBY_FABRIAL.get();}
        if(type == SAPPHIRE){return SAItems.TOGGLE_SAPPHIRE_FABRIAL.get();}
        if(type == HELIODOR){return SAItems.TOGGLE_HELIODOR_FABRIAL.get();}
        if(type == DIAMOND){return SAItems.TOGGLE_DIAMOND_FABRIAL.get();}
        if(type == EMERALD){return SAItems.TOGGLE_EMERALD_FABRIAL.get();}
        if(type == AMETHYST){return SAItems.TOGGLE_AMETHYST_FABRIAL.get();}
        return null;
    }

    public static GemType TOPAZ = new GemType(0);
    public static GemType SMOKESTONE = new GemType(1);
    public static GemType GARNET = new GemType(2);
    public static GemType ZIRCON = new GemType(3);
    public static GemType RUBY = new GemType(4);
    public static GemType SAPPHIRE = new GemType(5);
    public static GemType HELIODOR = new GemType(6);
    public static GemType DIAMOND = new GemType(7);
    public static GemType EMERALD = new GemType(8);
    public static GemType AMETHYST = new GemType(9);

    public static class GemType{
        int gemTypeId;
        public GemType(int id){
            this.gemTypeId = id;
        }
    }

    public interface IHasGemType {
        GemType getGemType();
    }

}
