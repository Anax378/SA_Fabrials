package com.anax.sa_fabrials.block.screen;

import com.anax.sa_fabrials.SAFabrials;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArtifabriansStationScreen extends AbstractContainerScreen<ArtifabriansStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SAFabrials.MOD_ID, "textures/gui/artifabrians_station_gui.png");
    public ArtifabriansStationScreen(ArtifabriansStationMenu artifabriansStationMenu, Inventory inventory, Component component) {
        super(artifabriansStationMenu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta){
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
