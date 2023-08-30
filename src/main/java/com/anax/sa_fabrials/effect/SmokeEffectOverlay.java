package com.anax.sa_fabrials.effect;

import com.anax.sa_fabrials.SAFabrials;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SmokeEffectOverlay implements IGuiOverlay {
    public static SmokeEffectOverlay INSTANCE = new SmokeEffectOverlay();

    private static final ResourceLocation SMOKE_OVERLAY = new ResourceLocation(SAFabrials.MOD_ID, "textures/mob_effect/smoke_overlay.png");
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();

        Player player = minecraft.player;

        if(player != null && player.hasEffect(SAEffects.SMOKE_EFFECT.get())){

            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, SMOKE_OVERLAY);

            Tesselator tesselator = Tesselator.getInstance();

            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D , height, -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(width, 0.0D  , -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D , 0.0D  , -90.0D).uv(0.0F, 0.0F).endVertex();

            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
