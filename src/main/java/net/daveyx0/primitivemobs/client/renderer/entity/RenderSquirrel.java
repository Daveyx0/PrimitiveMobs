package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelSquirrel;
import net.daveyx0.primitivemobs.entity.passive.EntitySquirrel;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSquirrel extends RenderLiving<EntitySquirrel>
{
    private static final ResourceLocation SQUIRREL_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/squirrel/squirrel.png");
    
    public RenderSquirrel(RenderManager p_i47196_1_)
    {
        super(p_i47196_1_, new ModelSquirrel(), 0.3F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySquirrel entity)
    {
        return SQUIRREL_TEXTURES;
    }

}