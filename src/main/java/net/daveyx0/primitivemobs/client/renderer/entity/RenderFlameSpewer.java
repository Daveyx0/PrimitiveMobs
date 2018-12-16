package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelFlameSpewer;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerFlameSpewerEyes;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerLavaSkin;
import net.daveyx0.primitivemobs.entity.monster.EntityFlameSpewer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFlameSpewer<T extends EntityLiving> extends RenderLiving<EntityFlameSpewer>
{
    private static final ResourceLocation FLAMESPEWER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/flamespewer/flamespewer.png");

    public RenderFlameSpewer(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelFlameSpewer(64, 64, false, true), 0.5f);
        this.addLayer(new LayerLavaSkin(this));
        this.addLayer(new LayerFlameSpewerEyes(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityFlameSpewer entity)
    {
        return FLAMESPEWER_TEXTURES;
    }
    
    /*
    protected void preRenderCallback(EntityFlameSpewer entitylivingbaseIn, float partialTickTime)
    {
    	GlStateManager.scale(2, 2, 2);
    }*/
}
    
