package net.daveyx0.primitivemobs.client.renderer.entity.layer;

import net.daveyx0.primitivemobs.client.models.ModelMimic;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderMimic;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerMimicMouth implements LayerRenderer<EntityMimic>
{
    private static final ResourceLocation MIMICMOUTH_TEXTURES = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "textures/entity/mimic/chest.png");
    private final RenderMimic mimicRenderer;
    private final ModelBase mimicModel = new ModelMimic();

    public LayerMimicMouth(RenderMimic mimicRendererIn)
    {
        this.mimicRenderer = mimicRendererIn;
    }

    public void doRenderLayer(EntityMimic entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {     
        this.mimicModel.setModelAttributes(this.mimicRenderer.getMainModel());
        this.mimicRenderer.bindTexture(MIMICMOUTH_TEXTURES);
        this.mimicModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}