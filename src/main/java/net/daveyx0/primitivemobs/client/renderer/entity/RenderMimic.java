package net.daveyx0.primitivemobs.client.renderer.entity;

import java.util.Calendar;

import net.daveyx0.primitivemobs.client.models.ModelMimic;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerMimicMouth;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderMimic<T extends EntityLiving> extends RenderLiving<EntityMimic>
{
    private static final ResourceLocation CHEST_TEXTURES = new ResourceLocation("minecraft", "textures/entity/chest/normal.png");
    private static final ResourceLocation CHEST_CHRISTMAS_TEXTURES = new ResourceLocation("minecraft", "textures/entity/chest/christmas.png");
    private boolean isChristmas;
    
    public RenderMimic(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelMimic(), 0.45f);
        this.addLayer(new LayerMimicMouth(this));
        
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
        {
            this.isChristmas = true;
        }
    }
    
    protected void isInAir(EntityMimic entitymimic)
    {
        if (!entitymimic.onGround)
        {
            if (entitymimic.motionY > 0.1D && entitymimic.motionY < 0.5D)
            {
                GlStateManager.rotate(25F, -1F, 0.0F, 0.0F);
            }
            else
            {
            	GlStateManager.rotate((float)(entitymimic.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }
    
    protected float handleRotationFloat(EntityMimic mimic, float f)
    {
        float f1 = mimic.nomminge + (mimic.nommingb - mimic.nomminge) * f;
        float f2 = mimic.nommingd + (mimic.nommingc - mimic.nommingd) * f;
        return (MathHelper.sin(f1) + 0.2F) * f2 + mimic.rotation;
        
    }
    
    protected void preRenderCallback(EntityMimic p_77041_1_, float p_77041_2_)
    {
    	EntityMimic mimic = (EntityMimic)p_77041_1_;
        isInAir(mimic);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMimic mimic)
    {
    	if(this.isChristmas)
    	{
    		return CHEST_CHRISTMAS_TEXTURES;
    	}
    	else
    	{
        	return CHEST_TEXTURES;
    	}

    }
}
