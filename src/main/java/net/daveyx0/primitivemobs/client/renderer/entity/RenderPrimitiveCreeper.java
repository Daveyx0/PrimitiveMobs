package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerPrimitiveCreeperCharge;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPrimitiveCreeper extends RenderLiving<EntityPrimitiveCreeper>
{
    private static final ResourceLocation ROCKETCREEPER_TEXTURES = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "textures/entity/rocketcreeper/rocketcreeper.png");
    private static final ResourceLocation FESTIVECREEPER_TEXTURES = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "textures/entity/festivecreeper/festivecreeper.png");
    private static final ResourceLocation SUPPORTCREEPER_TEXTURES = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "textures/entity/supportcreeper/supportcreeper.png");

    public RenderPrimitiveCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelCreeper(), 0.5F);
        this.addLayer(new LayerPrimitiveCreeperCharge(this));
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityPrimitiveCreeper entitylivingbaseIn, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
        
        if(entitylivingbaseIn instanceof EntityRocketCreeper)
        {
        	EntityRocketCreeper creeper = (EntityRocketCreeper)entitylivingbaseIn;
        	 if (!creeper.onGround && creeper.isRocket())
 	         {
 	            if (creeper.motionY > 0.1D && creeper.motionY < 0.5D)
 	            {
 	            	GlStateManager.rotate(25F, -1F, 0.0F, 0.0F);
 	            }
 	            else
 	            {
 	            	GlStateManager.rotate((float)(creeper.motionY * 175D), -1F, 0.0F, 0.0F);
 	            }
 	         }
        }
    }

    /**
     * Gets an RGBA int color multiplier to apply.
     */
    protected int getColorMultiplier(EntityPrimitiveCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int)(f * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int)(f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }


    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityPrimitiveCreeper entity)
    {
    	if(entity instanceof EntityFestiveCreeper) {return FESTIVECREEPER_TEXTURES;}
    	else if(entity instanceof EntityRocketCreeper) {return ROCKETCREEPER_TEXTURES;}
    	else {return SUPPORTCREEPER_TEXTURES;}

    }
}