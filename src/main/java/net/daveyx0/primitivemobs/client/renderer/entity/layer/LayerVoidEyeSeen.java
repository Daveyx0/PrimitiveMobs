package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelVoidEye;
import net.daveyx0.primitivemobs.entity.monster.EntityVoidEye;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerVoidEyeSeen implements LayerRenderer<EntityVoidEye>
{
	private static final ResourceLocation VOIDEYESEEN_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/voideye/voideye_seen.png");
    private final RenderLiving eyeRenderer;
    private final ModelVoidEye modelVoidEye;

    public LayerVoidEyeSeen(RenderLiving eyeRenderer)
    {
        this.eyeRenderer = eyeRenderer;
        modelVoidEye = new ModelVoidEye(false);
    }

    public void doRenderLayer(EntityVoidEye entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        EntityLivingBase entitylivingbase = entitylivingbaseIn.getTargetedEntity();

    	this.modelVoidEye.setModelAttributes(this.eyeRenderer.getMainModel());
        this.eyeRenderer.bindTexture(VOIDEYESEEN_TEXTURES);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        if (entitylivingbase != null)
        {
            GlStateManager.disableDepth();
            GlStateManager.disableFog();
        }

        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        if (entitylivingbaseIn.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }

        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        this.modelVoidEye.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        i = entitylivingbaseIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.eyeRenderer.setLightmap(entitylivingbaseIn);
        if (entitylivingbase != null)
        {
            GlStateManager.enableDepth();
            GlStateManager.enableFog();
        }
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}