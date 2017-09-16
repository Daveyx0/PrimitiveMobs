package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.renderer.entity.RenderHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeldItemHaunted implements LayerRenderer<EntityHauntedTool>
{
    private final RenderHauntedTool toolRenderer;

    public LayerHeldItemHaunted(RenderHauntedTool toolRendererIn)
    {
        this.toolRenderer = toolRendererIn;
    }

    public void doRenderLayer(EntityHauntedTool entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        ItemStack itemstack = entity.getHeldItemMainhand();

        float f1 = entity.floatinge + (entity.floatingb - entity.floatinge) * limbSwing;
        float f2 = entity.floatingd + (entity.floatingc - entity.floatingd) * limbSwing;
        float f3 = (MathHelper.sin(f1) + 0.5F) * f2 * 1.5F;
        float var6 = f3 + 0.5F;
        
        if (itemstack != null)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0, 0.0F,-0.75F);
            GlStateManager.translate(0.0, var6 - 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            //GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);

            Item item = itemstack.getItem();
            Minecraft minecraft = Minecraft.getMinecraft();

            if (item instanceof ItemBlock) //&& minecraft.getBlockRendererDispatcher().isEntityBlockAnimated(Block.getBlockFromItem(item)))
            {
                GlStateManager.translate(0.0F, 0.0625F, -0.25F);
                //GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(0.375F, -0.375F, 0.375F);
            }
            else
            {
                GlStateManager.translate(0.0F, -1.35F, 0.0F);
                GlStateManager.scale(0.7F, 0.7F, 0.7F);
            }
            
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_DST_ALPHA);
            minecraft.getItemRenderer().renderItem(entity, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();

            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}