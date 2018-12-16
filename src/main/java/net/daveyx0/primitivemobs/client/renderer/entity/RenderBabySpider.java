package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerBabySpiderEyes;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBabySpider<T extends EntityBabySpider> extends RenderLiving<T>
{
    private static final ResourceLocation BABY_SPIDER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/spiderfamily/babyspider.png");
    private static final ResourceLocation SPIDER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/spiderfamily/spider.png");

    public RenderBabySpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSpider(), 0.3F);
        this.addLayer(new LayerBabySpiderEyes(this));
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn)
    {
        return 180.0F;
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityBabySpider entitybabyspider, float partialTickTime)
    {
    	switch(entitybabyspider.getGrowthLevel())
    	{
    		case 0:
    	    	GlStateManager.scale(0.5F, 0.5F, 0.5F);
    	    	break;
    		case 1:
    	    	GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	    	break;
    		case 2:
    	    	GlStateManager.scale(0.7F, 0.7F, 0.7F);
    	    	break;
    		case 3:
    	    	GlStateManager.scale(0.8F, 0.8F, 0.8F);
    	    	break;
    		case 4:
    	    	GlStateManager.scale(0.9F, 0.9F, 0.9F);
    	    	break;
    		case 5:
    	    	GlStateManager.scale(1.0F, 1.0F, 1.0F);
    	    	break;
    	}

    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBabySpider entity)
    {
    	if(entity.getGrowthLevel() >= 5)
    	{
    		return SPIDER_TEXTURES;
    	}
        return BABY_SPIDER_TEXTURES;
    }
}
