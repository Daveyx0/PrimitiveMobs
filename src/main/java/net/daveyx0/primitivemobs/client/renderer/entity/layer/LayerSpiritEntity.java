package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.common.capabilities.CapabilitySummonableEntity;
import net.daveyx0.primitivemobs.common.capabilities.ISummonableEntity;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerSpiritEntity implements LayerRenderer {

	private RenderLivingBase renderer;
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("primitivemobs","textures/entity/summoner/summon_effect.png");

	public LayerSpiritEntity(RenderLivingBase renderer) {
		this.renderer = renderer;
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (entitylivingbaseIn instanceof EntityLiving) {
			ISummonableEntity capability = EntityUtil.getCapability(entitylivingbaseIn, CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null);
			if (capability != null && capability.isSummonedEntity()) {
				
				boolean flag = entitylivingbaseIn.isInvisible();
	            GlStateManager.depthMask(!flag);
	            this.renderer.bindTexture(LIGHTNING_TEXTURE);
	            GlStateManager.matrixMode(5890);
	            GlStateManager.loadIdentity();
	            float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
	            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
	            GlStateManager.matrixMode(5888);
	            GlStateManager.enableBlend();
	            float f1 = 0.5F;
	            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
	            GlStateManager.disableLighting();
	            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	            this.renderer.getMainModel().setModelAttributes(this.renderer.getMainModel());
	            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
	            this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
	            GlStateManager.matrixMode(5890);
	            GlStateManager.loadIdentity();
	            GlStateManager.matrixMode(5888);
	            GlStateManager.enableLighting();
	            GlStateManager.disableBlend();
	            GlStateManager.depthMask(flag);
				/*
				GlStateManager.depthMask(true);
				GL11.glEnable(GL11.GL_CULL_FACE);
				this.renderer.bindTexture(new ResourceLocation(getStoneType(renderer.getMainModel(), 1)));
				this.renderer.getMainModel().render(entitylivingbaseIn, f, 0, 0, f3, f4, f5);
				GL11.glDisable(GL11.GL_CULL_FACE);*/
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}