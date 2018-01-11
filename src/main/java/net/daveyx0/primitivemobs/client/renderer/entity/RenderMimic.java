package net.daveyx0.primitivemobs.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.daveyx0.primitivemobs.client.models.ModelMimic;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerGroveSpriteStump;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerMimicMouth;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

public class RenderMimic<T extends EntityLiving> extends RenderLiving<EntityMimic>
{
    private static final ResourceLocation CHEST_TEXTURES = new ResourceLocation("minecraft", "textures/entity/chest/normal.png");
    
    public RenderMimic(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelMimic(), 0.45f);
        this.addLayer(new LayerMimicMouth(this));
    }
    
    protected void isInAir(EntityMimic entitymimic)
    {
        if (!entitymimic.onGround)
        {
            if (entitymimic.motionY > 0.1D && entitymimic.motionY < 0.5D)
            {
                GlStateManager.rotate(25F, -1F, 0.0F, 0.0F);
            }
            else
            {
            	GlStateManager.rotate((float)(entitymimic.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }
    
    protected float handleRotationFloat(EntityMimic mimic, float f)
    {
        float f1 = mimic.nomminge + (mimic.nommingb - mimic.nomminge) * f;
        float f2 = mimic.nommingd + (mimic.nommingc - mimic.nommingd) * f;
        return (MathHelper.sin(f1) + 0.2F) * f2 + mimic.rotation;
        
    }
    
    protected void preRenderCallback(EntityMimic p_77041_1_, float p_77041_2_)
    {
    	EntityMimic mimic = (EntityMimic)p_77041_1_;
        isInAir(mimic);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMimic mimic)
    {
    	return CHEST_TEXTURES;
    }
}
