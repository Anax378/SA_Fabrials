package com.anax.sa_fabrials;

import com.anax.sa_fabrials.block.SABlocks;
import com.anax.sa_fabrials.block.entity.SABlockEntities;
import com.anax.sa_fabrials.block.screen.ArtifabriansStationScreen;
import com.anax.sa_fabrials.block.screen.CrystalScreen;
import com.anax.sa_fabrials.block.screen.SAMenuTypes;
import com.anax.sa_fabrials.block.screen.SprenCatchingStationScreen;
import com.anax.sa_fabrials.effect.SAEffects;
import com.anax.sa_fabrials.entity.SAEntityTypes;
import com.anax.sa_fabrials.item.SAItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

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

        SAItems.register(eventBus);
        SABlocks.register(eventBus);
        SABlockEntities.register(eventBus);
        SAMenuTypes.register(eventBus);
        SAEntityTypes.register(eventBus);
        SAEffects.register(eventBus);

        MinecraftForge.EVENT_BUS.register(SAEventHandler.class);

    }

    @SubscribeEvent
    public static void do_setup(FMLClientSetupEvent event){
        EntityRenderers.register(SAEntityTypes.THROWN_FABRIAL.get(), ThrownItemRenderer::new);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }
    private void clientSetup(final FMLClientSetupEvent event){
        MenuScreens.register(SAMenuTypes.CRYSTAL_MENU.get(), CrystalScreen::new);
        MenuScreens.register(SAMenuTypes.SPREN_CATCHING_STATION_MENU.get(), SprenCatchingStationScreen::new);
        MenuScreens.register(SAMenuTypes.ARTIFABRIANS_STATION_MENU.get(), ArtifabriansStationScreen::new);
    }
}
