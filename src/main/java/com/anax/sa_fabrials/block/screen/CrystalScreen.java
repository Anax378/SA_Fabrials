package com.anax.sa_fabrials.block.screen;

import com.anax.sa_fabrials.SAFabrials;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrystalScreen extends AbstractContainerScreen<CrystalMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SAFabrials.MOD_ID, "textures/gui/crystal_gui.png");

    public CrystalScreen(CrystalMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);


        blit(pPoseStack,x + 146, y + 8, 176, 0, 22, Math.round(70*(1-(((float)menu.data.get(0))/((float)menu.data.get(1))))));

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta){
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
