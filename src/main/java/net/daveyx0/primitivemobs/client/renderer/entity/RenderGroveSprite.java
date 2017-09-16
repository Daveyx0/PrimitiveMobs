package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelChameleon;
import net.daveyx0.primitivemobs.client.models.ModelGroveSprite;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerGroveSpriteLeaves;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerGroveSpriteStump;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemCustom;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderGroveSprite<T extends EntityLiving> extends RenderLiving<EntityGroveSprite> {

    private static final ResourceLocation GROVEBASE_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/grovesprite/grovebase.png");

    public RenderGroveSprite(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelGroveSprite(0), 0.3f);
        this.addLayer(new LayerHeldItemCustom(this));
        this.addLayer(new LayerGroveSpriteLeaves(this));
        this.addLayer(new LayerGroveSpriteStump(this));
    }
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityGroveSprite entity)
    {
        return GROVEBASE_TEXTURES;
    }
    
    @Override
    public void doRender(EntityGroveSprite entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix(); 
        float[] RGB = entity.getLogRGB();
        

        GlStateManager.color(RGB[0]/255.0F, RGB[1]/255.0F, RGB[2]/255.0F, 1.0F);
        GlStateManager.popMatrix();
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected void preRenderCallback(EntityGroveSprite entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.translate(0, 0.9f, 0);
    }
}
