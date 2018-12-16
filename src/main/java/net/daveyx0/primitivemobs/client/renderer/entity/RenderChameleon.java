package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChameleon<T extends EntityLiving> extends RenderLiving<EntityChameleon>
{
    private static final ResourceLocation CHAMELEON_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/chameleon/chameleon.png");

    public RenderChameleon(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelChameleon(), 0.4f);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityChameleon entity)
    {
        return CHAMELEON_TEXTURES;
    }
    
    @Override
    public void doRender(EntityChameleon entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        
        float[] RGB = entity.getSkinRGB();
        
        GlStateManager.pushMatrix();   
        GlStateManager.color(RGB[0]/255.0F, RGB[1]/255.0F, RGB[2]/255.0F, 1.0F);
        GlStateManager.popMatrix();
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }
   
}