package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelFilchLizard;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemCustom;
import net.daveyx0.primitivemobs.entity.passive.EntityFilchLizard;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFilchLizard<T extends EntityLiving> extends RenderLiving<EntityFilchLizard>
{
    private static final ResourceLocation FILCHLIZARD_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/filchlizard/filchlizard.png");

    public RenderFilchLizard(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelFilchLizard(), 0.4f);
        this.addLayer(new LayerHeldItemCustom(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityFilchLizard entity)
    {
        return FILCHLIZARD_TEXTURES;
    }
    
}