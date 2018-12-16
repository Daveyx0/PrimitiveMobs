package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelBrainSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerBrainSlimeGel implements LayerRenderer<EntityBrainSlime>
{
	private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("primitivemobs", "textures/entity/brainslime/slime_brain.png");
    private final RenderBrainSlime slimeRenderer;
    private final ModelBase slimeModel = new ModelBrainSlime(0);

    public LayerBrainSlimeGel(RenderBrainSlime slimeRendererIn)
    {
        this.slimeRenderer = slimeRendererIn;
    }

    public void doRenderLayer(EntityBrainSlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isInvisible())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeRenderer.bindTexture(SLIME_TEXTURES);
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}