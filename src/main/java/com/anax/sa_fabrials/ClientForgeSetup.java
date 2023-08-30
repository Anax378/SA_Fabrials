package com.anax.sa_fabrials;

import com.anax.sa_fabrials.effect.SAEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SAFabrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeSetup {
    static Minecraft minecraft = Minecraft.getInstance();
    private static final ResourceLocation SMOKE_OVERLAY = new ResourceLocation(SAFabrials.MOD_ID, "textures/mob_effect/smoke_overlay.png");

    @SubscribeEvent
    public static void RenderEvent(RenderGuiOverlayEvent.Post event){
        Player player = minecraft.player;
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        if(player != null && player.hasEffect(SAEffects.SMOKE_EFFECT.get())){
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, SMOKE_OVERLAY);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();

            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 5.0F).endVertex();
            bufferbuilder.vertex(width, (double) height, -90.0D).uv(5.0F, 5.0F).endVertex();
            bufferbuilder.vertex(width, 0.0D, -90.0D).uv(5.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();

            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(0.0F, 0.0F, 1.0F, 1.0F);

        }
    }

}
