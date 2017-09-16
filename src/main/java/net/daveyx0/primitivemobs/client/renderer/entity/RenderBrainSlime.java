package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelBrainSlime;
import net.daveyx0.primitivemobs.client.models.ModelTreasureSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerBrainSlimeGel;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerTreasureSlimeGel;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBrainSlime<T extends EntityLiving> extends RenderLiving<EntityBrainSlime>
{
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/brainslime/slime_brain.png");

    public RenderBrainSlime(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelBrainSlime(1), 0.4f);
        this.addLayer(new LayerBrainSlimeGel(this));
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityBrainSlime entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = 0.25F * (float)entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityBrainSlime entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSlimeSize();
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        float f4 = entitylivingbaseIn.suckinge + (entitylivingbaseIn.suckingb - entitylivingbaseIn.suckinge) * partialTickTime;
        float f5 = entitylivingbaseIn.suckingd + (entitylivingbaseIn.suckingc - entitylivingbaseIn.suckingd) * partialTickTime;
        float f6 = (MathHelper.sin(f4) + 0.5F) * f5 * 1.5F;
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1 + f6, f3 * f1);
    }
    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBrainSlime entity)
    {
        return SLIME_TEXTURES;
    }
}