package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.multimob.util.ImageUtil;
import net.daveyx0.primitivemobs.client.models.ModelFlameSpewer;
import net.daveyx0.primitivemobs.entity.monster.EntityFlameSpewer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public class LayerLavaSkin  implements LayerRenderer<EntityLiving> {

	public RenderLiving renderer;
	
	public ModelBase lavaModel;
	public ResourceLocation lavaTexture;
	public LayerLavaSkin(RenderLiving renderer)
	{
		ResourceLocation fluidTexture = FluidRegistry.LAVA.getFlowing();
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluidTexture.toString());
        lavaTexture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("minecraft:lava_flow", new DynamicTexture(ImageUtil.getBufferedImage(sprite)));
        
		this.lavaModel = new ModelFlameSpewer(32, 512, true, true);
		this.renderer = renderer;
	}
	
	@Override
	public void doRenderLayer(EntityLiving entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		EntityFlameSpewer spewer = (EntityFlameSpewer)entitylivingbaseIn;
		GlStateManager.pushMatrix();
		this.lavaModel.setModelAttributes(renderer.getMainModel());
		if(this.lavaModel instanceof ModelFlameSpewer)
		{
			((ModelFlameSpewer) this.lavaModel).setFlameSpewerModelAttributes((ModelFlameSpewer)renderer.getMainModel());
		}
        this.renderer.bindTexture(lavaTexture);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
        GlStateManager.translate(0.0F, (float)Math.round(f) * 0.03125F, 0.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.disableLighting();
        if (entitylivingbaseIn.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, (10f - spewer.getAttackTime())/10f);
		this.lavaModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
	
	@Override
	public boolean shouldCombineTextures() {

		return false;
	}

}
