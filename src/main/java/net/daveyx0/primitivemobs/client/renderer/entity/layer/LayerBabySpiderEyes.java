package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerBabySpiderEyes implements LayerRenderer<EntityBabySpider>
{
	private static final ResourceLocation SPIDER_EYES = new ResourceLocation("primitivemobs", "textures/entity/spider_eyes.png");
    private final RenderLiving spiderRenderer;

    public LayerBabySpiderEyes(RenderLiving spiderRendererIn)
    {
        this.spiderRenderer = spiderRendererIn;
    }
    
    public void doRenderLayer(EntityBabySpider entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {

    	GlStateManager.enableBlend();
        if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag()))
        {
            int i1 = 25;
            int i2 = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
            int j1 = EnumDyeColor.values().length;
            int k1 = i2 % j1;
            int l = (i2 + 1) % j1;
            float f = ((float)(entitylivingbaseIn.ticksExisted % 25) + partialTicks) / 25.0F;
            float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k1));
            float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
            GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
        }
        else
        {
        	if(entitylivingbaseIn.getGrowthLevel() < 5)
        	{
        		GlStateManager.color(166f/255f, 2f/255f, 2f/255f);
        	}
        	else
        	{
                float[] afloat = EntitySheep.getDyeRgb(entitylivingbaseIn.getEyeColor());
                GlStateManager.color(afloat[0], afloat[1], afloat[2]);

        	}

        }
        this.spiderRenderer.getMainModel().setModelAttributes(this.spiderRenderer.getMainModel());
        this.spiderRenderer.bindTexture(SPIDER_EYES);
        this.spiderRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
        /*
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
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
        
        if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag()))
        {
            int i1 = 25;
            int i2 = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
            int j1 = EnumDyeColor.values().length;
            int k1 = i2 % j1;
            int l = (i2 + 1) % j1;
            float f = ((float)(entitylivingbaseIn.ticksExisted % 25) + partialTicks) / 25.0F;
            float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k1));
            float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
            GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
        }
        else
        {
            float[] afloat = EntitySheep.getDyeRgb(entitylivingbaseIn.getEyeColor());
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
        }
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        this.spiderRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        i = entitylivingbaseIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.spiderRenderer.setLightmap(entitylivingbaseIn);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();*/
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}