package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.renderer.entity.RenderTreasureSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeldItemSlime implements LayerRenderer<EntityTreasureSlime>
{
    private final RenderTreasureSlime slimeRenderer;

    public LayerHeldItemSlime(RenderTreasureSlime slimeRendererIn)
    {
        this.slimeRenderer = slimeRendererIn;
    }

    public void doRenderLayer(EntityTreasureSlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        ItemStack itemstack = entitylivingbaseIn.getHeldItemMainhand();

        if (!itemstack.isEmpty())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);

            GlStateManager.translate(0.0, 0.0, 0.0F);
            Item item = itemstack.getItem();
            Minecraft minecraft = Minecraft.getMinecraft();

            if (item instanceof ItemBlock)// && minecraft.getBlockRendererDispatcher().isEntityBlockAnimated(Block.getBlockFromItem(item)))
            {
                GlStateManager.translate(0.0F, -1.15F, 0.0F);
                //GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(0.525F, -0.525F, 0.525F);
            }
            else if(item.isFull3D())
            {
                GlStateManager.translate(0.0F, -1.35F, 0.0F);
                GlStateManager.scale(0.35F, 0.35F, 0.35F);
            }
            else
            {
                GlStateManager.translate(0.0F, -1.35F, 0.0F);
                GlStateManager.scale(0.7F, 0.7F, 0.7F);
            }
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_DST_ALPHA);
            minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
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