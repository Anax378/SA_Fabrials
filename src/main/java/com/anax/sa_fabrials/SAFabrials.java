package com.anax.sa_fabrials;

import com.anax.sa_fabrials.block.ModBlocks;
import com.anax.sa_fabrials.block.entity.ModBlockEntities;
import com.anax.sa_fabrials.block.screen.CrystalScreen;
import com.anax.sa_fabrials.block.screen.ModMenuTypes;
import com.anax.sa_fabrials.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.stream.Collectors;
@Mod("sa_fabrials")
public class SAFabrials
{

    public static final String MOD_ID = "sa_fabrials";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SAFabrials()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }
    private void clientSetup(final FMLClientSetupEvent event){
        MenuScreens.register(ModMenuTypes.CRYSTAL_MENU.get(), CrystalScreen::new);
    }
}
