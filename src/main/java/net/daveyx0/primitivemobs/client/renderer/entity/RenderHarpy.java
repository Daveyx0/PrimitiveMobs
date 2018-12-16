package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelHarpy;
import net.daveyx0.primitivemobs.entity.monster.EntityHarpy;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHarpy<T extends EntityLiving> extends RenderLiving<EntityHarpy>
{
    private static final ResourceLocation HARPY_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/harpy/harpy.png");

    public RenderHarpy(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelHarpy(), 0.5f);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityHarpy entity)
    {
        return HARPY_TEXTURES;
    }
    
    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
	public float handleRotationFloat(EntityHarpy livingBase, float partialTicks)
    {
        return this.getCustomBob(livingBase, partialTicks);
    }

    private float getCustomBob(EntityHarpy harpy, float p_192861_2_)
    {
        float f = harpy.oFlap + (harpy.flap - harpy.oFlap) * p_192861_2_;
        float f1 = harpy.oFlapSpeed + (harpy.flapSpeed - harpy.oFlapSpeed) * p_192861_2_;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}