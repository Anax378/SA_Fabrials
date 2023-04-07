package com.anax.sa_fabrials.block.screen;

import com.anax.sa_fabrials.SAFabrials;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SprenCatchingStationScreen extends AbstractContainerScreen<SprenCatchingStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SAFabrials.MOD_ID, "textures/gui/spren_catching_station_gui.png");

    public SprenCatchingStationScreen(SprenCatchingStationMenu sprenCatchingStationMenu, Inventory inventory, Component title) {
        super(sprenCatchingStationMenu, inventory, title);
    }

    @Override
    protected void renderBg(PoseStack poseStack,float pPartialTick, int MouseX, int MouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
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
