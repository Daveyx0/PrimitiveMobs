package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelFilchLizard;
import net.daveyx0.primitivemobs.client.models.ModelGroveSprite;
import net.daveyx0.primitivemobs.client.models.ModelLilyLurker;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderGroveSprite;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.passive.EntityFilchLizard;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeldItemCustom implements LayerRenderer<EntityLivingBase>
{
    protected final RenderLivingBase<?> livingEntityRenderer;

    public LayerHeldItemCustom(RenderLivingBase<?> livingEntityRendererIn)
    {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    //Lazy handling of held item renders for my mobs :D
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = entitylivingbaseIn.getHeldItemMainhand();

        if (!itemstack.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }
            
            if(entitylivingbaseIn instanceof EntityGroveSprite)
            {
            	EntityGroveSprite sprite = (EntityGroveSprite)entitylivingbaseIn;
            	if(sprite.getSaplingAmount() > 0)
            	{
            		ModelGroveSprite spriteModel = (ModelGroveSprite)this.livingEntityRenderer.getMainModel();
            		this.renderHeldItemSprite(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, spriteModel.bipedRightArm);
            	}
            }
            else if(entitylivingbaseIn instanceof EntityFilchLizard)
            {
            	EntityFilchLizard sprite = (EntityFilchLizard)entitylivingbaseIn;
            	if(!sprite.getHeldItemMainhand().isEmpty())
            	{
            		ModelFilchLizard spriteModel = (ModelFilchLizard)this.livingEntityRenderer.getMainModel();
            		this.renderHeldItemLizard(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, spriteModel.Leg1);
            	}
            }else if (entitylivingbaseIn instanceof EntitySkeletonWarrior)
            {
            	EntitySkeletonWarrior sprite = (EntitySkeletonWarrior)entitylivingbaseIn;
            	if(!sprite.getBackItem().isEmpty())
            	{
            		itemstack = sprite.getBackItem();
            		ModelSkeleton spriteModel = (ModelSkeleton)this.livingEntityRenderer.getMainModel();
            		this.renderBackItemSkeletonWarrior(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, spriteModel.bipedBody);
            	}
            }
            else if (entitylivingbaseIn instanceof EntityLilyLurker)
            {
            	EntityLilyLurker sprite = (EntityLilyLurker)entitylivingbaseIn;
            	if(!sprite.getHeldItemMainhand().isEmpty())
            	{
            		itemstack = sprite.getHeldItemMainhand();
            		ModelLilyLurker spriteModel = (ModelLilyLurker)this.livingEntityRenderer.getMainModel();
            		this.renderLily(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, spriteModel.Root1);
            	}
            }
            GlStateManager.popMatrix();
        }
    }

    private void renderHeldItemSprite(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, ModelRenderer renderer)
    {
        if (!p_188358_2_.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (p_188358_1_.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            renderer.postRender(0.0625F);;
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = true;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F + 0.1F, 0.125F, -0.625F + 0.35f);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }
    
    private void renderHeldItemLizard(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, ModelRenderer renderer)
    {
        if (!p_188358_2_.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (p_188358_1_.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            //GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
            boolean flag = true;
            GlStateManager.translate(-0.55F, -1.0F, -0.05F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }
    
    private void renderBackItemSkeletonWarrior(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, ModelRenderer renderer)
    {
        if (!p_188358_2_.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (p_188358_1_.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            GlStateManager.rotate(90.0F, 0.0F, -1.0F, 0.0F);
            boolean flag = true;
            GlStateManager.translate(0.1F, 0.15F, -0.1F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }
    
    private void renderLily(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, ModelRenderer renderer)
    {
        if (!p_188358_2_.isEmpty())
        {
            GlStateManager.pushMatrix();

            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            boolean flag = true;
            GlStateManager.translate(-0.025F, -0.2F, -0.9F);
            GlStateManager.scale(2D, 2D, 2D);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}