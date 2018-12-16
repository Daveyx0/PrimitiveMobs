package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelGoblin;
import net.daveyx0.primitivemobs.entity.monster.EntityGoblin;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGoblin<T extends EntityLiving> extends RenderBiped<EntityGoblin>{

    private static final ResourceLocation GOBLIN_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/goblin/goblin.png");

    public RenderGoblin(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelGoblin(), 0.5f);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityGoblin entity)
    {
        return GOBLIN_TEXTURES;
    }
}
