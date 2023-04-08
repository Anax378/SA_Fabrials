package com.anax.sa_fabrials.entity.client;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.entity.ModEntityTypes;
import com.anax.sa_fabrials.entity.client.render.ThrownFabrialRenderer;
import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SAFabrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntityTypes.THROWN_FABRIAL.get(), ThrownFabrialRenderer::new);
    }
}
