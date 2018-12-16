package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveCreeper;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPrimitiveCreeper extends ModelCreeper
{
    public ModelPrimitiveCreeper()
    {
        this(0.0F);
    }

    
    public ModelPrimitiveCreeper(float p_i46366_1_)
    {
    	super(p_i46366_1_);
    }
    
    protected float field_78145_g = 8.0F;
    protected float field_78151_h = 4.0F;
    /**
     * Sets the models various rotation angles then renders the model.
     */
	@Override
    public void render(Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7)
    {
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, entityIn);
        if(entityIn instanceof EntityPrimitiveCreeper)
        {
        	EntityPrimitiveCreeper creeper = (EntityPrimitiveCreeper)entityIn;
        	
        	if(creeper.isChild())
        	{
        		float var8 = 2.0F;
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, this.field_78145_g * par7 + 0.125f, this.field_78151_h * par7 - 0.25F);
                this.head.render(par7);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
                GlStateManager.translate(0.0F, 28.0F * par7 - 0.25F, 0.0F);
                this.body.render(par7);
                this.leg1.render(par7);
                this.leg2.render(par7);
                this.leg3.render(par7);
                this.leg4.render(par7);
                GlStateManager.popMatrix();
        	}
            else
            {
                this.head.render(par7);
                this.body.render(par7);
                this.leg1.render(par7);
                this.leg2.render(par7);
                this.leg3.render(par7);
                this.leg4.render(par7);
            }
        }
        
    }
}
