package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerPrimitiveSpiderEyes;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveSpider;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMotherSpider<T extends EntityMotherSpider> extends RenderLiving<T>
{
    private static final ResourceLocation MOTHER_SPIDER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/spiderfamily/motherspider.png");

    public RenderMotherSpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSpider(), 0.3F);
        this.addLayer(new LayerPrimitiveSpiderEyes(this));
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn)
    {
        return 180.0F;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(T entity)
    {
        return MOTHER_SPIDER_TEXTURES;
    }
}