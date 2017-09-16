package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelGroveSprite;
import net.daveyx0.primitivemobs.client.models.ModelTreasureSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderGroveSprite;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderTreasureSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGroveSpriteLeaves implements LayerRenderer<EntityGroveSprite>
{
    private static final ResourceLocation GROVELEAF_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/grovesprite/groveleaf.png");
    private final RenderGroveSprite spriteRenderer;
    private final ModelBase spriteModel = new ModelGroveSprite(1);

    public LayerGroveSpriteLeaves(RenderGroveSprite spriteRendererIn)
    {
        this.spriteRenderer = spriteRendererIn;
    }

    public void doRenderLayer(EntityGroveSprite entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        float[] RGB = entitylivingbaseIn.getLeavesRGB();
        
        GlStateManager.pushMatrix();   
        GlStateManager.color(RGB[0]/255.0F, RGB[1]/255.0F, RGB[2]/255.0F, 1.0F); 
        GlStateManager.popMatrix();
        
        this.spriteModel.setModelAttributes(this.spriteRenderer.getMainModel());
        this.spriteRenderer.bindTexture(GROVELEAF_TEXTURES);
        this.spriteModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}