package net.daveyx0.primitivemobs.client.models;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGroveSprite extends ModelBase
{

    public ModelGroveSprite(int render)
    {      
        textureWidth = 64;
        textureHeight = 32;
        
    	bipedHead = new ModelRenderer(this, 0, 0);
    	bipedHead.addBox(-3F, -6F, -3F, 6, 6, 6, 0);
        bipedHead.setRotationPoint(0.0F, 0.0F  - 1f, 0.0F);
        bipedHead.setTextureSize(64, 32);
    	bipedBody = new ModelRenderer(this,24, 12);
    	bipedBody.addBox(-2.5F, 0.0F, -2F, 5, 6, 4, 0);
    	bipedBody.setRotationPoint(0.0F, 0.0625F - 1f, 0.0F);
    	bipedBody.setTextureSize(64, 32);
        
        if(render == 0)
        {
        	bipedHeadwear = new ModelRenderer(this,24, 0);
        	bipedHeadwear.addBox(-3F, -6F, 2.0F, 6, 6, 2, 0);
        	bipedHeadwear.setRotationPoint(0.0F, 0.0F  - 1f, 0.0F);
        	bipedHeadwear.setTextureSize(64, 32);
        	bipedRightArm = new ModelRenderer(this,12, 12);
        	bipedRightArm.addBox(-2F, -1F, -1.5F, 3, 5, 3, 0);
        	bipedRightArm.setRotationPoint(-3.5F, 1.0F  - 1f, 0.0F);
        	bipedRightArm.setTextureSize(64, 32);
        	bipedLeftArm = new ModelRenderer(this,12, 12);
        	bipedLeftArm.mirror = true;
        	bipedLeftArm.addBox(-1F, -1F, -1.5F, 3, 5, 3, 0);
        	bipedLeftArm.setRotationPoint(3.5F, 1.0F  - 1f, 0.0F);
        	bipedLeftArm.setTextureSize(64, 32);
        	bipedRightLeg = new ModelRenderer(this,0, 12);
        	bipedRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0);
        	bipedRightLeg.setRotationPoint(-1.5F, 6F  - 1f, 0.0F);
        	bipedRightLeg.setTextureSize(64, 32);
        	bipedLeftLeg = new ModelRenderer(this,0, 12);
        	bipedLeftLeg.mirror = true;
        	bipedLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0);
        	bipedLeftLeg.setRotationPoint(1.5F, 6F  - 1f, 0.0F);
        	bipedLeftLeg.setTextureSize(64, 32);
        	stem = new ModelRenderer(this,24, 8);
        	stem.addBox(-0.5F, -9F, 0.0F, 1, 3, 1, 0);
        	stem.setRotationPoint(0.0F, 0.0F  - 1f, 0.0F);
        	stem.setTextureSize(64, 32);
        }
        else
        {
        	leaf = new ModelRenderer[3];
        	for(int i = 0; i < 3; i++)
        	{
        		leaf[i] = new ModelRenderer(this,0, 22);
        		leaf[i].addBox(-1.5F, -9F, -5.25F, 3, 1, 5, 0);
        		leaf[i].setRotationPoint(0.0F, 0.0F - 1f, 0.0F);
        		leaf[i].setTextureSize(64, 32);
        	}
        }

    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
      	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      	
      	if(bipedHeadwear != null)
      	{
      		bipedRightArm.render(f5);
      		bipedLeftArm.render(f5);
      		bipedRightLeg.render(f5);
      		bipedLeftLeg.render(f5);
      		bipedHeadwear.render(f5);
      		stem.render(f5);
      	}
      	
      	if(leaf != null)
      	{
      		for(int i = 0; i < 3; i++)
        	{
            	GL11.glPushMatrix();
            	GL11.glTranslatef(0.0F, 0.8125F, 0.0F);
            	GL11.glRotatef(leaf[i].rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
           		GL11.glRotatef(leaf[i].rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
            	GL11.glTranslatef(0.0F, -0.8125F, 0.0F);
            	leaf[i].rotateAngleX = 0.0F;
            	leaf[i].rotateAngleY = 2.0F * ((float)i + 1.0F);
            	leaf[i].render(f5);
            	GL11.glPopMatrix();
        	}
      	}
      	
  		bipedHead.render(f5);
  		bipedBody.render(f5);

    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);  
      
      bipedHead.rotateAngleY = f3 / 57.29578F;
      bipedHead.rotateAngleX = f4 / 57.29578F;
      
    if(bipedHeadwear != null)
    {
      stem.rotateAngleY = bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
      stem.rotateAngleX = bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
      
      bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * f1;
      bipedRightArm.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * f1;
      bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * f1;
      bipedLeftLeg.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * f1;
    }

    
    if(leaf != null)
    {
      for(int i = 0; i < 3; i++)
      {
          leaf[i].rotateAngleY = f3 / 57.29578F;;
          leaf[i].rotateAngleX = f4 / 57.29578F * 0.5f;
      }
    }
    }

    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer stem;
    public ModelRenderer leaf[];
}