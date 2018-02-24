package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelTreasureSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderTreasureSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerTreasureSlimeGel implements LayerRenderer<EntityTreasureSlime>
{
	private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/treasureslime/slime_treasure.png");
	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("minecraft","textures/misc/enchanted_item_glint.png");
    private final RenderTreasureSlime slimeRenderer;
    private final ModelBase slimeModel = new ModelTreasureSlime(0);

    public LayerTreasureSlimeGel(RenderTreasureSlime slimeRendererIn)
    {
        this.slimeRenderer = slimeRendererIn;
    }

    public void doRenderLayer(EntityTreasureSlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        float[] RGB = entitylivingbaseIn.getSkinRGB();
        GlStateManager.pushMatrix();  
        GlStateManager.color(RGB[0]/255.0F, RGB[1]/255.0F, RGB[2]/255.0F, 1.0F);
        GlStateManager.popMatrix();
        
        if (!entitylivingbaseIn.isInvisible())
        {
            //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeRenderer.bindTexture(SLIME_TEXTURES);
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
        }
        
        if (entitylivingbaseIn.getHeldItemMainhand().isItemEnchanted() || entitylivingbaseIn.getHeldItemMainhand().hasEffect()) {
			
			boolean flag = entitylivingbaseIn.isInvisible();
            GlStateManager.depthMask(!flag);
            this.slimeRenderer.bindTexture(LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.02F, f * 0.02F, 0);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.5F;
            GlStateManager.color(0.5F, 0.5F, 0.85F, 0.5f);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            GlStateManager.scale(1.05f, 1.05f, 1.05f);
            GlStateManager.translate(0f, -0.075f, 0f);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag);
	}
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}