package com.anax.sa_fabrials;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.effect.SmokeEffectOverlay;
import com.anax.sa_fabrials.entity.SAEntityTypes;
import com.anax.sa_fabrials.entity.client.model.SmokeCloudModel;
import com.anax.sa_fabrials.entity.client.render.SmokeCloudRenderer;
import com.anax.sa_fabrials.entity.client.render.ThrownFabrialRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SAFabrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(SAEntityTypes.THROWN_FABRIAL.get(), ThrownFabrialRenderer::new);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(SAEntityTypes.SMOKE_CLOUD.get(), SmokeCloudRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(SmokeCloudRenderer.SMOKE_CLOUD_MODEL_LAYER_LOCATION, SmokeCloudModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event){
        event.registerBelowAll("smoke_overlay", SmokeEffectOverlay.INSTANCE);
    }
}


