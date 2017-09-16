package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelEmpty;
import net.daveyx0.primitivemobs.client.models.ModelEnchantedBook;
import net.daveyx0.primitivemobs.client.models.ModelTreasureSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemHaunted;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerTreasureSlimeGel;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderEchantedBook<T extends EntityLiving> extends RenderLiving 
{
	private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "textures/entity/enchantedbook/book.png");
	
    public RenderEchantedBook(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelEnchantedBook(), 0.4f);
    }
    
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        EntityEnchantedBook entitybook = (EntityEnchantedBook)entityliving;
        float f1 = entitybook.floatinge + (entitybook.floatingb - entitybook.floatinge) * f;
        float f2 = entitybook.floatingd + (entitybook.floatingc - entitybook.floatingd) * f;
        float f3 = (MathHelper.sin(f1) + 0.5F) * f2 * 1.5F;
        float var6 = f3 - 0.8F;
        
        	GlStateManager.translate(0.0F, var6, 0.0F);
        	GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
    }
    
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        EntityEnchantedBook entityDEnchantedBook = (EntityEnchantedBook)entityliving;
        float f1 = entityDEnchantedBook.floatinge + (entityDEnchantedBook.floatingb - entityDEnchantedBook.floatinge) * f;
        float f2 = entityDEnchantedBook.floatingd + (entityDEnchantedBook.floatingc - entityDEnchantedBook.floatingd) * f;
        return (MathHelper.sin(f1) + 0.2F) * f2;
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityEnchantedBook entity, double x, double y, double z, float entityYaw, float partialTicks)
    {	    
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return BOOK_TEXTURES;
	}

}
