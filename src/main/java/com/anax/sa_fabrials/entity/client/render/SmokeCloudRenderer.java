package com.anax.sa_fabrials.entity.client.render;

import com.anax.sa_fabrials.SAFabrials;
import com.anax.sa_fabrials.entity.client.model.SmokeCloudModel;
import com.anax.sa_fabrials.entity.custom.SmokeCloud;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.fml.common.Mod;

public class SmokeCloudRenderer extends EntityRenderer<SmokeCloud> {
    private static final ResourceLocation SMOKE_CLOUD_LOCATION = new ResourceLocation(SAFabrials.MOD_ID, "textures/entities/smoke_cloud.png");
    public static final ModelLayerLocation SMOKE_CLOUD_MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SAFabrials.MOD_ID, "smoke_cloud"), "main");
    private final SmokeCloudModel model;

    private final float SCALE_OFFSET = 0.00625f;
    public SmokeCloudRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new SmokeCloudModel(pContext.bakeLayer(SMOKE_CLOUD_MODEL_LAYER_LOCATION));
    }

    @Override
    public boolean shouldRender(SmokeCloud pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(SmokeCloud pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPackedLight = 15728640;
        float size = pEntity.getSize();
        float lifeTime = pEntity.getLifeTime();
        float sizeFactor = lifeTime < 0 ? 1-(Math.abs(lifeTime)/20f) : 1;
        pPoseStack.pushPose();
        pPoseStack.translate(0, -(size+SCALE_OFFSET), 0);
        pPoseStack.scale((size+SCALE_OFFSET)*sizeFactor, (size+SCALE_OFFSET)*sizeFactor, (size+SCALE_OFFSET)*sizeFactor);


        VertexConsumer vertexConsumer = pBuffer.getBuffer(this.model.renderType(SMOKE_CLOUD_LOCATION));
        this.model.renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SmokeCloud pEntity) {
        return SMOKE_CLOUD_LOCATION;
    }
}
