package com.anax.sa_fabrials.block.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArtifabriansStationScreen extends AbstractContainerScreen<ArtifabriansStationMenu> {
    public ArtifabriansStationScreen(ArtifabriansStationMenu artifabriansStationMenu, Inventory inventory, Component component) {
        super(artifabriansStationMenu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int mouseX, int mouseY) {
        //TODO: fix this
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta){
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
