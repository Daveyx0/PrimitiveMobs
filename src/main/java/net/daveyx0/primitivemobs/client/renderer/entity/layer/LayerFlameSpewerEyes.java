package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelFlameSpewer;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderFlameSpewer;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.monster.EntityFlameSpewer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerFlameSpewerEyes implements LayerRenderer<EntityFlameSpewer>
{
    private static final ResourceLocation FLAMESPEWER_EYES_TEXTURES = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "textures/entity/flamespewer/flamespewer_lava.png");
    private final RenderFlameSpewer renderer;
    private final ModelBase model = new ModelFlameSpewer(64, 64, false, false);

    public LayerFlameSpewerEyes(RenderFlameSpewer spewerRenderer)
    {
        this.renderer = spewerRenderer;
    }

    public void doRenderLayer(EntityFlameSpewer spewer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {   
    	GlStateManager.enableBlend();
    	GlStateManager.color(1, 1, 1, (10f - spewer.getAttackTime())/10f);
        this.model.setModelAttributes(this.renderer.getMainModel());
        this.renderer.bindTexture(FLAMESPEWER_EYES_TEXTURES);
        this.model.render(spewer, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}