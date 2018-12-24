package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelYeti;
import net.daveyx0.primitivemobs.entity.monster.EntityYeti;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderYeti extends RenderLiving<EntityYeti>{

    private static final ResourceLocation YETI_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/yeti/yeti.png");

    public RenderYeti(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelYeti(), 0.5f);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityYeti entity)
    {
        return YETI_TEXTURES;
    }
}