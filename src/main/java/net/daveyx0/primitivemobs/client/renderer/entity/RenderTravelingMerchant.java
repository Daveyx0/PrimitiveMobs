package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelTravelingMerchant;
import net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTravelingMerchant extends RenderLiving<EntityTravelingMerchant>
{
    private static final ResourceLocation TRAVELINGMERCHANT_TEXTURE = new ResourceLocation("primitivemobs","textures/entity/villager/travelingmerchant.png");
    
    public RenderTravelingMerchant(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelTravelingMerchant(), 0.5F);
        //this.addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
        //this.addLayer(new LayerHeldItemCustom(this));
    }

    public ModelTravelingMerchant getMainModel()
    {
        return (ModelTravelingMerchant)super.getMainModel();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTravelingMerchant entity)
    {
        return TRAVELINGMERCHANT_TEXTURE;
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityTravelingMerchant entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;

        if (entitylivingbaseIn.getGrowingAge() < 0)
        {
            f = (float)((double)f * 0.5D);
            this.shadowSize = 0.25F;
        }
        else
        {
            this.shadowSize = 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }
}