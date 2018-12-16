package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.monster.EntityFlameSpewer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class ModelFlameSpewer extends ModelBase
{
  //fields
    ModelRenderer body;
    ModelRenderer mouth;
    ModelRenderer[] tentacles = new ModelRenderer[8];
    float[] offset =  new float[8];
    boolean renderTentacles;
    boolean isLava;
  
  public ModelFlameSpewer(int textureWidth, int textureHeight, boolean isLava, boolean renderTentacles)
  {
    this.textureWidth = textureWidth;
    this.textureHeight = textureHeight;
    this.renderTentacles = renderTentacles;
    this.isLava = isLava;
    
      body = new ModelRenderer(this, 0, 0);
      body.addBox(-7F, -16F, -7F, 14, 16, 14);
      body.setRotationPoint(0F, 21F, 0F);
      body.setTextureSize(32, 512);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      mouth = this.isLava ? new ModelRenderer(this, 16, 0) : new ModelRenderer(this, 43, 0);
      mouth.addBox(-2F, -5F, -10F, 4, 4, 4);
      mouth.setRotationPoint(0F, 21F, 0F);
      mouth.setTextureSize(32, 512);
      mouth.mirror = true;
      setRotation(mouth, 0.1570796F, 0F, 0F);
      
      for(int i = 0; i < tentacles.length; i++)
      {
    	  tentacles[i] = this.isLava ? new ModelRenderer(this, 0, 16) : new ModelRenderer(this, 0, 30);
    	  tentacles[i].addBox(-2F, 0F, -26F, 4, 4, 24);
    	  tentacles[i].setRotationPoint(0F, 18F, 0F);
    	  tentacles[i].setTextureSize(32, 512);
    	  tentacles[i].mirror = true;
          setRotation(tentacles[i], 0.4537856F, (float)Math.toRadians(22.5 + 45 * i), 0F);
          offset[i] = 0;
      }

  }
  
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
  {
    super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    EntityFlameSpewer spewer = (EntityFlameSpewer)entityIn;
    GlStateManager.pushMatrix();

	float f = 1 + spewer.getAttackSignal();
	GlStateManager.translate(0, -f + 0.9, 0);
    GlStateManager.scale(f, f, f);
    body.render(scale);
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();
    float e = MathHelper.sin((float)entityIn.ticksExisted * 0.5F) * 0.03F + 1;
	GlStateManager.translate(0, -0.1, 0);
    GlStateManager.scale(e, e, e);
    mouth.render(scale);
    GlStateManager.popMatrix();
    
    if(renderTentacles)
    {
        for(int i = 0; i < tentacles.length; i++)
        {
      	  tentacles[i].render(scale);
        }
    }
    Item mob = Items.ACACIA_BOAT;
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
  {
    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    EntityFlameSpewer spewer = (EntityFlameSpewer)entityIn;
    
    body.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
    mouth.rotateAngleY = body.rotateAngleY;
    
    if(!isLava)
    {
    for(int i = 0; i < tentacles.length; i++)
    {
        if(offset[i] == 0){offset[i] = (spewer.getRNG().nextFloat() - spewer.getRNG().nextFloat()) + 0.01F;}
        
        if(spewer.isInDanger())
        {
        	idleTentacle(tentacles[i], (float)spewer.ticksExisted * 0.75F + offset[i], 10F);
        }
        else
        {
            idleTentacle(tentacles[i], (float)spewer.ticksExisted * 0.15F + offset[i], 4F);
        }
    }
    }

  }
  
  public static void idleTentacle(ModelRenderer model, float e, float f)
  {
	    model.rotateAngleX = MathHelper.sin(e) * f * 0.017453292F;
  }
  
  public void setFlameSpewerModelAttributes(ModelFlameSpewer model)
  {
      for(int i = 0; i < tentacles.length; i++)
      {
    	  tentacles[i].rotateAngleX = model.tentacles[i].rotateAngleX;
      }
      super.setModelAttributes(model);
  }
  
  
}