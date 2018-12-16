package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelTreasureSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerTreasureSlimeGel;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTreasureSlime<T extends EntityLiving> extends RenderLiving<EntityTreasureSlime>
{
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/treasureslime/slime_treasure.png");

    public RenderTreasureSlime(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelTreasureSlime(1), 0.4f);
        this.addLayer(new LayerHeldItemSlime(this));
        this.addLayer(new LayerTreasureSlimeGel(this));
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityTreasureSlime entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = 0.25F * (float)entity.getSlimeSize();
        
        float[] RGB = entity.getSkinRGB();
        
        GlStateManager.pushMatrix();   
        GlStateManager.color(RGB[0]/255.0F, RGB[1]/255.0F, RGB[2]/255.0F, 1.0F);
        GlStateManager.popMatrix();
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityTreasureSlime entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSlimeSize();
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTreasureSlime entity)
    {
        return SLIME_TEXTURES;
    }
}