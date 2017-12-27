package net.daveyx0.primitivemobs.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.client.models.ModelChameleon;
import net.daveyx0.primitivemobs.client.models.ModelTrollager;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemCustom;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTrollager<T extends EntityLiving> extends RenderLiving<EntityTrollager>
{
    private static final ResourceLocation TROLLAGER_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/troll/troll.png");
    private static final ResourceLocation TROLLAGER_STONED_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/troll/troll_stoned.png");

    public RenderTrollager(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelTrollager(), 2.35f);
        this.addLayer(new LayerHeldItemCustom(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTrollager entity)
    {
    	if(entity.isStone())
    	{
    		return TROLLAGER_STONED_TEXTURES;
    	}
    	else
    	{
    		return TROLLAGER_TEXTURES;
    	}
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityTrollager entitylivingbaseIn, float partialTickTime)
    {
    	GlStateManager.scale(2F, 2F, 2F);
    }
    
    @Override
    public void doRender(EntityTrollager entity, double x, double y, double z, float entityYaw, float partialTicks)
    {  
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
   
}