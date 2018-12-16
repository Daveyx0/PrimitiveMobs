package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelEmpty;
import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerHeldItemHaunted;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderHauntedTool<T extends EntityLiving> extends RenderLiving 
{
	//Used so Journeymap can generate an icon
	private static final ResourceLocation FAKE_TEXTURE = new ResourceLocation("primitivemobs", "textures/entity/mimic/haunted_tool.png");
	
    public RenderHauntedTool(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelEmpty(), 0.2f);
        this.addLayer(new LayerHeldItemHaunted(this));
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityHauntedTool entity, double x, double y, double z, float entityYaw, float partialTicks)
    {	
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected float setDeathMaxRotation(EntityHauntedTool entityHauntedPickaxe)
    {
        return 90.0F;
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return FAKE_TEXTURE;
	}

}
