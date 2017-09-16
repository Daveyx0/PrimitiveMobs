package net.daveyx0.primitivemobs.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.client.models.ModelBlazingJuggernaut;
import net.daveyx0.primitivemobs.client.models.ModelChameleon;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
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
public class RenderBlazingJuggernaut<T extends EntityLiving> extends RenderLiving<EntityBlazingJuggernaut>
{
    private static final ResourceLocation BLAZINGJUGGERNAUT_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/blazingjuggernaut/blazingjuggernaut.png");

    public RenderBlazingJuggernaut(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelBlazingJuggernaut(), 0.5f);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBlazingJuggernaut entity)
    {
        return BLAZINGJUGGERNAUT_TEXTURES;
    }
    
    @Override
    public void doRender(EntityBlazingJuggernaut entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
   
}