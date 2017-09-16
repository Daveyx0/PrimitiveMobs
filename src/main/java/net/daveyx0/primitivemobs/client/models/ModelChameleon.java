package net.daveyx0.primitivemobs.client.models;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelChameleon extends ModelBase{

	 //fields
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer tail;
    ModelRenderer head;
    ModelRenderer head2;
    ModelRenderer eyeleft;
    ModelRenderer eyeright;
    ModelRenderer legfrontleft1;
    ModelRenderer legfrontright1;
    ModelRenderer legfrontleft2;
    ModelRenderer legfrontright2;
    ModelRenderer legbackleft1;
    ModelRenderer legbackright1;
    ModelRenderer legbackleft2;
    ModelRenderer legbackright2;
    ModelRenderer tongue1;
    ModelRenderer tongue2;
  
  public ModelChameleon()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      body1 = new ModelRenderer(this, 0, 0);
      body1.addBox(-2F, -1F, 0F, 4, 4, 6);
      body1.setRotationPoint(0F, 20F, -4F);
      body1.setTextureSize(64, 32);
      body1.mirror = true;
      setRotation(body1, 0.1487144F, 0F, 0F);
      body2 = new ModelRenderer(this, 0, 11);
      body2.addBox(-1.5F, -2F, 5.5F, 3, 3, 2);
      body2.setRotationPoint(0F, 20F, -4F);
      body2.setTextureSize(64, 32);
      body2.mirror = true;
      setRotation(body2, -0.0743572F, 0F, 0F);
      tail = new ModelRenderer(this, 0, 17);
      tail.addBox(-1F, -1F, -0.2F, 2, 4, 4);
      tail.setRotationPoint(0F, 20F, 3F);
      tail.setTextureSize(64, 32);
      tail.mirror = true;
      setRotation(tail, -0.4461433F, 0F, -0.5948578F);
      head = new ModelRenderer(this, 25, 0);
      head.addBox(-1.5F, -1.5F, -3.5F, 3, 3, 4);
      head.setRotationPoint(0F, 21F, -4F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0.2974289F, 0F, 0F);
      head2 = new ModelRenderer(this, 25, 8);
      head2.addBox(-1F, -2F, -3F, 2, 4, 2);
      head2.setRotationPoint(0F, 21F, -4F);
      head2.setTextureSize(64, 32);
      head2.mirror = true;
      setRotation(head2, -0.7063936F, 0F, 0F);
      eyeleft = new ModelRenderer(this, 34, 8);
      eyeleft.addBox(1.3F, -0.5F, -2F, 1, 1, 1);
      eyeleft.setRotationPoint(0F, 21F, -4F);
      eyeleft.setTextureSize(64, 32);
      eyeleft.mirror = true;
      setRotation(eyeleft, 0.1115358F, 0.2230717F, -0.0743572F);
      eyeright = new ModelRenderer(this, 34, 11);
      eyeright.addBox(-2.3F, -0.5F, -2F, 1, 1, 1);
      eyeright.setRotationPoint(0F, 21F, -4F);
      eyeright.setTextureSize(64, 32);
      eyeright.mirror = true;
      setRotation(eyeright, 0.1115358F, -0.2230705F, 0.074351F);
      legfrontleft1 = new ModelRenderer(this, 45, 0);
      legfrontleft1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
      legfrontleft1.setRotationPoint(2F, 21F, -3.5F);
      legfrontleft1.setTextureSize(64, 32);
      legfrontleft1.mirror = true;
      setRotation(legfrontleft1, 1.115358F, 0.2974367F, 0.1115513F);
      legfrontright1 = new ModelRenderer(this, 50, 0);
      legfrontright1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
      legfrontright1.setRotationPoint(-2F, 21F, -3.5F);
      legfrontright1.setTextureSize(64, 32);
      legfrontright1.mirror = true;
      setRotation(legfrontright1, 1.115358F, -0.297439F, -0.111544F);
      legfrontleft2 = new ModelRenderer(this, 45, 4);
      legfrontleft2.addBox(-0.5F, -0.5F, 2F, 1, 3, 1);
      legfrontleft2.setRotationPoint(2F, 21F, -3.5F);
      legfrontleft2.setTextureSize(64, 32);
      legfrontleft2.mirror = true;
      setRotation(legfrontleft2, -0.4635966F, 0.297439F, 0.111544F);
      legfrontright2 = new ModelRenderer(this, 50, 4);
      legfrontright2.addBox(-0.5F, -0.5F, 2F, 1, 3, 1);
      legfrontright2.setRotationPoint(-2F, 21F, -3.5F);
      legfrontright2.setTextureSize(64, 32);
      legfrontright2.mirror = true;
      setRotation(legfrontright2, -0.4635966F, -0.297439F, -0.111544F);
      legbackleft1 = new ModelRenderer(this, 45, 9);
      legbackleft1.addBox(-0.7F, 0F, -0.5F, 1, 2, 1);
      legbackleft1.setRotationPoint(1F, 21F, 3F);
      legbackleft1.setTextureSize(64, 32);
      legbackleft1.mirror = true;
      setRotation(legbackleft1, -1.152532F, -0.8179311F, -0.1487144F);
      legbackright1 = new ModelRenderer(this, 50, 9);
      legbackright1.addBox(-0.3F, 0F, -0.5F, 1, 2, 1);
      legbackright1.setRotationPoint(-1F, 21F, 3F);
      legbackright1.setTextureSize(64, 32);
      legbackright1.mirror = true;
      setRotation(legbackright1, -1.152528F, 0.8179334F, 0.1487195F);
      legbackleft2 = new ModelRenderer(this, 45, 13);
      legbackleft2.addBox(-0.7F, -0.5F, -3F, 1, 3, 1);
      legbackleft2.setRotationPoint(1F, 21F, 3F);
      legbackleft2.setTextureSize(64, 32);
      legbackleft2.mirror = true;
      setRotation(legbackleft2, 0.426418F, -0.8179311F, -0.1487195F);
      legbackright2 = new ModelRenderer(this, 50, 13);
      legbackright2.addBox(-0.3F, -0.5F, -3F, 1, 3, 1);
      legbackright2.setRotationPoint(-1F, 21F, 3F);
      legbackright2.setTextureSize(64, 32);
      legbackright2.mirror = true;
      setRotation(legbackright2, 0.426418F, 0.8179311F, 0.1487195F);
      tongue1 = new ModelRenderer(this, 25, 15);
      tongue1.addBox(-0.5F, 0.5F, -7.4F, 1, 0, 4);
      tongue1.setRotationPoint(0F, 21F, -4F);
      tongue1.setTextureSize(64, 32);
      tongue1.mirror = true;
      setRotation(tongue1, 0.2974216F, 0F, 0F);
      tongue2 = new ModelRenderer(this, 25, 15);
      tongue2.addBox(-0.5F, 0F, -8.4F, 1, 1, 1);
      tongue2.setRotationPoint(0F, 21F, -4F);
      tongue2.setTextureSize(64, 32);
      tongue2.mirror = true;
      setRotation(tongue2, 0.2974216F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    
    EntityChameleon chameleon = (EntityChameleon)entity;
    
    if(chameleon.isChild())
    {
        float var8 = 2.0F;
        GL11.glPushMatrix();
        GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
        GL11.glTranslatef(0.0F, 23.0F * f5, 0.0F);
        renderModel(f5);
        GL11.glPopMatrix();
    }
    else
    {
  	  renderModel(f5);
    }
  }
  
  private void renderModel(float f5)
  {
	  body1.render(f5);
  	  body2.render(f5);
  	  tail.render(f5);
  	  head.render(f5);
  	  head2.render(f5);
  	  eyeleft.render(f5);
  	  eyeright.render(f5);
  	  legfrontleft1.render(f5);
  	  legfrontright1.render(f5);
  	  legfrontleft2.render(f5);
  	  legfrontright2.render(f5);
  	  legbackleft1.render(f5);
  	  legbackright1.render(f5);
  	  legbackleft2.render(f5);
  	  legbackright2.render(f5);
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
      head.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.2974289F ;
      head.rotateAngleY = f3 / (180F / (float)Math.PI);
      head2.rotateAngleX = f4 / (180F / (float)Math.PI) - 0.7063936F ;
      head2.rotateAngleY = f3 / (180F / (float)Math.PI);
      eyeleft.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.1396263F ;
      eyeleft.rotateAngleY = f3 / (180F / (float)Math.PI);
      eyeright.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.1396263F ;
      eyeright.rotateAngleY = f3 / (180F / (float)Math.PI);
      
      tongue1.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.2974216F ;
      tongue1.rotateAngleY = f3 / (180F / (float)Math.PI);
      tongue2.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.2974216F ;
      tongue2.rotateAngleY = f3 / (180F / (float)Math.PI);
      /* Tail.rotateAngleY = f3 / (270F / (float)Math.PI); */
      legfrontleft1.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 + 1.115358F;
      legfrontleft2.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 - 0.4635966F;
      legfrontright1.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 + 1.115358F;
      legfrontright2.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 - 0.4635966F;
      
      legbackleft1.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 - 1.152532F;
      legbackleft2.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 + 0.426418F;
      legbackright1.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 - 1.152532F;
      legbackright2.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 + 0.426418F;
  
  }

}
