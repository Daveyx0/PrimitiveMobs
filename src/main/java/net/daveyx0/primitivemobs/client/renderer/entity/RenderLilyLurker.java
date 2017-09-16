package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelChameleon;
import net.daveyx0.primitivemobs.client.models.ModelLilyLurker;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemCustom;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLilyLurker<T extends EntityLiving> extends RenderLiving<EntityLilyLurker>
{
    private static final ResourceLocation LILYLURKER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/lilylurker/lilylurker.png");

    public RenderLilyLurker(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelLilyLurker(), 0.5f);
        this.addLayer(new LayerHeldItemCustom(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityLilyLurker entity)
    {
        return LILYLURKER_TEXTURES;
    }
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLilyLurker lurker, float par2)
    {
    	if(lurker.isCamouflaged())
	 	{
	 		GlStateManager.translate(0.0F, -0.24F, 0.0F);
	 	}
    }
    
   
}