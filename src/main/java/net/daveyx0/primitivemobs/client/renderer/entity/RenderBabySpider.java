package net.daveyx0.primitivemobs.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerPrimitiveSpiderEyes;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBabySpider<T extends EntityBabySpider> extends RenderLiving<T>
{
    private static final ResourceLocation BABY_SPIDER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/spiderfamily/babyspider.png");

    public RenderBabySpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSpider(), 0.3F);
        this.addLayer(new LayerPrimitiveSpiderEyes(this));
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn)
    {
        return 180.0F;
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityBabySpider entitylivingbaseIn, float partialTickTime)
    {
    	GlStateManager.scale(0.5F, 0.5F, 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(T entity)
    {
        return BABY_SPIDER_TEXTURES;
    }
}
