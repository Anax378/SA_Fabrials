package com.anax.sa_fabrials.item;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.item.custom.GemstoneItem;
import com.anax.sa_fabrials.item.custom.ThrowableFabrialItem;
import com.anax.sa_fabrials.util.fabrial.GemCapacities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SAFabrials.MOD_ID);
    public static final RegistryObject<Item> TOPAZ = ITEMS.register("topaz",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<Item> sapphire = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<Item> SMOKESTONE = ITEMS.register("smokestone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<Item> GARNET = ITEMS.register("garnet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<Item> HELIODOR = ITEMS.register("heliodor",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB)));

    public static final RegistryObject<GemstoneItem> TOPAZ_GEM = ITEMS.register("topaz_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.TOPAZ_GEM_CAPACITY, 500, 500));
    public static final RegistryObject<GemstoneItem> SMOKESTONE_GEM = ITEMS.register("smokestone_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.SMOKESTONE_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> GARNET_GEM = ITEMS.register("garnet_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.GARNET_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> ZIRCON_GEM = ITEMS.register("zircon_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.ZIRCON_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> RUBY_GEM = ITEMS.register("ruby_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.RUBY_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> SAPPHIRE_GEM = ITEMS.register("sapphire_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.SAPPHIRE_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> HELIODOR_GEM = ITEMS.register("heliodor_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.HELIODOR_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> DIAMOND_GEM = ITEMS.register("diamond_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.DIAMOND_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> EMERALD_GEM = ITEMS.register("emerald_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.EMERALD_GEM_CAPACITY, 500,500));
    public static final RegistryObject<GemstoneItem> AMETHYST_GEM = ITEMS.register("amethyst_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), GemCapacities.AMETHYST_GEM_CAPACITY, 500,500));


    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_TOPAZ_FABRIAL = ITEMS.register("throwable_topaz_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.TOPAZ_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_SMOKESTONE_FABRIAL = ITEMS.register("throwable_smokestone_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.SMOKESTONE_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_GARNET_FABRIAL = ITEMS.register("throwable_garnet_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.GARNET_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_ZIRCON_FABRIAL = ITEMS.register("throwable_zircon_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.ZIRCON_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_RUBY_FABRIAL = ITEMS.register("throwable_ruby_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.RUBY_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_SAPPHIRE_FABRIAL = ITEMS.register("throwable_sapphire_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.SAPPHIRE_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_HELIODOR_FABRIAL = ITEMS.register("throwable_heliodor_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.HELIODOR_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_DIAMOND_FABRIAL = ITEMS.register("throwable_diamond_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.DIAMOND_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_EMERALD_FABRIAL = ITEMS.register("throwable_emerald_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.EMERALD_GEM_CAPACITY, 500, 500, 0));
    public static final RegistryObject<ThrowableFabrialItem> THROWABLE_AMETHYST_FABRIAL = ITEMS.register("throwable_amethyst_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), GemCapacities.AMETHYST_GEM_CAPACITY, 500, 500, 0));

    public static final RegistryObject<ThrowableFabrialItem> THROWN_FABRIAL = ITEMS.register("thrown_fabrial",
            ()-> new ThrowableFabrialItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1).fireResistant(), 0, 0, 0, 0));

    public static final RegistryObject<GemstoneItem> CREATIVE_GEM = ITEMS.register("creative_gem",
            () -> new GemstoneItem(new Item.Properties().tab(ModCreativeModeTab.SA_FABRIALS_MOD_TAB).stacksTo(1), 2000, 500,500){
                @Override
                public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
                    return new ItemStormlightStorageProvider(100000000, 100000000, 100000000, 100000000, stack){
                        @Override
                        public int receiveStormlight(int maxReceive, boolean simulate) {
                            return maxReceive;
                        }

                        @Override
                        public int extractStormlight(int maxExtract, boolean simulate) {
                            return maxExtract;
                        }

                        @Override
                        public boolean canExtract(Direction dir) {
                            return true;
                        }

                        @Override
                        public boolean canReceive(Direction dir) {
                            return true;
                        }
                    };
                }
                @Override
                public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
                   //super.appendHoverText(itemStack, level, componentList, tooltipFlag);
                    componentList.add(new TranslatableComponent("tooltip.sa_fabrials.stored_stormlight").append("∞/∞"));
                }
            });





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
