package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelSheepman;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerSheepmanWool;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepman;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSheepman extends RenderLiving<EntitySheepman>
{
    private static final ResourceLocation SHEEPMAN_TEXTURE = new ResourceLocation("primitivemobs","textures/entity/villager/sheepman.png");
    
    public RenderSheepman(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSheepman(0), 0.5F);
        this.addLayer(new LayerSheepmanWool(this));
    }

    public ModelSheepman getMainModel()
    {
        return (ModelSheepman)super.getMainModel();

    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySheepman entity)
    {
        return SHEEPMAN_TEXTURE;
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntitySheepman entitylivingbaseIn, float partialTickTime)
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