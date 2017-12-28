package net.daveyx0.primitivemobs.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.client.models.ModelChameleon;
import net.daveyx0.primitivemobs.client.models.ModelDodo;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDodo<T extends EntityLiving> extends RenderLiving<EntityDodo>
{
    private static final ResourceLocation DODO_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/rareanimals/dodo.png");

    public RenderDodo(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelDodo(), 0.4f);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDodo entity)
    {
        return DODO_TEXTURES;
    }
    
    @Override
    public void doRender(EntityDodo entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }
   
}