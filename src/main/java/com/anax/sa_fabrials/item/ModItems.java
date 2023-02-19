package com.anax.sa_fabrials.item;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.item.custom.PerfectGemstoneItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SAFabrials.MOD_ID);
    public static final RegistryObject<Item> TOPAZ = ITEMS.register("topaz",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<Item> sapphire = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<Item> SMOKESTONE = ITEMS.register("smokestone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<Item> GARNET = ITEMS.register("garnet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<Item> HELIODOR = ITEMS.register("heliodor",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB)));

    public static final RegistryObject<PerfectGemstoneItem> PERFECT_TOPAZ = ITEMS.register("perfect_topaz",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 64000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_SMOKESTONE = ITEMS.register("perfect_smokestone",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 1000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_GARNET = ITEMS.register("perfect_garnet",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 8000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_ZIRCON = ITEMS.register("perfect_zircon",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 12000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_RUBY = ITEMS.register("perfect_ruby",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 20000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_SAPPHIRE = ITEMS.register("perfect_sapphire",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 28000));

    public static final RegistryObject<PerfectGemstoneItem> PERFECT_HELIODOR = ITEMS.register("perfect_heliodor",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 16000));

    public static final RegistryObject<PerfectGemstoneItem> PERFECT_DIAMOND = ITEMS.register("perfect_diamond",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 32000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_EMERALD = ITEMS.register("perfect_emerald",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 4000));
    public static final RegistryObject<PerfectGemstoneItem> PERFECT_AMETHYST = ITEMS.register("perfect_amethyst",
            () -> new PerfectGemstoneItem(new Item.Properties().tab(ModCreativeModeTab.TEST_MOD_TAB).stacksTo(1), 2000));





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
