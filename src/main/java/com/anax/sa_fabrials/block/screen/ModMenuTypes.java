package com.anax.sa_fabrials.block.screen;

import com.anax.sa_fabrials.SAFabrials;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, SAFabrials.MOD_ID);

    public static final RegistryObject<MenuType<CrystalMenu>> CRYSTAL_MENU =
            registerMenuType(CrystalMenu::new, "crystal_menu");

    public static final RegistryObject<MenuType<SprenCatchingStationMenu>> SPREN_CATCHING_STATION_MENU =
            registerMenuType(SprenCatchingStationMenu::new, "spren_catching_station_menu");

    public static final RegistryObject<MenuType<ArtifabriansStationMenu>> ARTIFABRIANS_STATION_MENU =
            registerMenuType(ArtifabriansStationMenu::new, "spren_catching_station_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>>
    registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
