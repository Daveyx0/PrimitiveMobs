package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelGroveSprite;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderGroveSprite;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGroveSpriteStump implements LayerRenderer<EntityGroveSprite>
{
    private static final ResourceLocation GROVESTUMP_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/grovesprite/grovestump.png");
    private final RenderGroveSprite spriteRenderer;
    private final ModelBase spriteModel = new ModelGroveSprite(0);

    public LayerGroveSpriteStump(RenderGroveSprite spriteRendererIn)
    {
        this.spriteRenderer = spriteRendererIn;
    }

    public void doRenderLayer(EntityGroveSprite entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        float[] RGB = entitylivingbaseIn.getLogTopRGB();
        
        GlStateManager.pushMatrix();   
        GlStateManager.color(RGB[0]/255.0F, RGB[1]/255.0F, RGB[2]/255.0F, 1.0F); 
        GlStateManager.popMatrix();
        
        this.spriteModel.setModelAttributes(this.spriteRenderer.getMainModel());
        this.spriteRenderer.bindTexture(GROVESTUMP_TEXTURES);
        this.spriteModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}