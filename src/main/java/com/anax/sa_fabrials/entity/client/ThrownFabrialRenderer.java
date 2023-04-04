package com.anax.sa_fabrials.entity.client;

import com.anax.sa_fabrials.entity.custom.ThrownFabrial;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ThrownFabrialRenderer extends ThrownItemRenderer<ThrownFabrial> {

    public ThrownFabrialRenderer(EntityRendererProvider.Context context, float v, boolean b) {
        super(context, v, b);
    }
}
