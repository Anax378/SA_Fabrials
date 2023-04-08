package com.anax.sa_fabrials.entity.client.render;

import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ThrownFabrialRenderer extends ThrownItemRenderer<ThrownFabrial> {
    public ThrownFabrialRenderer(EntityRendererProvider.Context context, float v, boolean b) {
        super(context, v, b);
    }
    public ThrownFabrialRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0f, false);
    }

}
