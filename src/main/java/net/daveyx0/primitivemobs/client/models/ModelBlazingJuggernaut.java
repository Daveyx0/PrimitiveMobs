package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBlazingJuggernaut extends ModelBase
{
	 //fields
    ModelRenderer Head;
    ModelRenderer RightArm;
    ModelRenderer Body;
    ModelRenderer LeftArm;
    ModelRenderer RightShoulder;
    ModelRenderer LeftShoulder;
    ModelRenderer[] Sticks = new ModelRenderer[12];
  
  public ModelBlazingJuggernaut()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Head = new ModelRenderer(this, 8, 17);
      Head.addBox(-3F, -5F, -2.5F, 6, 5, 5);
      Head.setRotationPoint(0F, -1F, 0F);
      Head.setTextureSize(128, 64);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      RightArm = new ModelRenderer(this, 0, 27);
      RightArm.addBox(5F, -4.5F, -2.5F, 3, 9, 4);
      RightArm.setRotationPoint(0F, 4F, 0F);
      RightArm.setTextureSize(128, 64);
      RightArm.mirror = true;
      setRotation(RightArm, 0F, -0.0698132F, -0.0349066F);
      Body = new ModelRenderer(this, 0, 0);
      Body.addBox(-6F, -4.5F, -4F, 12, 9, 8);
      Body.setRotationPoint(0F, 4F, 0F);
      Body.setTextureSize(128, 64);
      Body.mirror = true;
      setRotation(Body, 0F, 0F, 0F);
      LeftArm = new ModelRenderer(this, 0, 27);
      LeftArm.addBox(-8F, -4.5F, -2.5F, 3, 9, 4);
      LeftArm.setRotationPoint(0F, 4F, 0F);
      LeftArm.setTextureSize(128, 64);
      LeftArm.mirror = true;
      setRotation(LeftArm, 0F, 0.0698132F, 0.0349066F);
      RightShoulder = new ModelRenderer(this, 30, 17);
      RightShoulder.addBox(3.5F, -7.5F, -4.5F, 5, 5, 7);
      RightShoulder.setRotationPoint(0F, 4F, 0F);
      RightShoulder.setTextureSize(128, 64);
      RightShoulder.mirror = true;
      setRotation(RightShoulder, 0F, -0.1396263F, 0F);
      LeftShoulder = new ModelRenderer(this, 30, 17);
      LeftShoulder.addBox(-8.5F, -7.5F, -4.5F, 5, 5, 7);
      LeftShoulder.setRotationPoint(0F, 4F, 0F);
      LeftShoulder.setTextureSize(128, 64);
      LeftShoulder.mirror = true;
      setRotation(LeftShoulder, 0F, 0.1396263F, 0F);
      
      for (int i = 0; i < this.Sticks.length; ++i)
      {
          this.Sticks[i] = new ModelRenderer(this, 0, 18);
          this.Sticks[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }
  }
  
  public int func_78104_a()
  {
      return 8;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Head.render(f5);
    RightArm.render(f5);
    Body.render(f5);
    LeftArm.render(f5);
    RightShoulder.render(f5);
    LeftShoulder.render(f5);
    for (int i = 0; i < this.Sticks.length; ++i)
    {
        this.Sticks[i].render(f5);
    }
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
  {
      float f6 = par3 * (float)Math.PI * -0.1F;
      int i;

      for (i = 0; i < 4; ++i)
      {
          this.Sticks[i].rotationPointY = 6.0F + MathHelper.cos(((float)(i * 2) + par3) * 0.25F);
          this.Sticks[i].rotationPointX = MathHelper.cos(f6) * 9.0F;
          this.Sticks[i].rotationPointZ = MathHelper.sin(f6) * 9.0F;
          ++f6;
      }

      f6 = ((float)Math.PI / 4F) + par3 * (float)Math.PI * 0.03F;

      for (i = 4; i < 8; ++i)
      {
          this.Sticks[i].rotationPointY = 5.0F + MathHelper.cos(((float)(i * 2) + par3) * 0.25F);
          this.Sticks[i].rotationPointX = MathHelper.cos(f6) * 11.0F;
          this.Sticks[i].rotationPointZ = MathHelper.sin(f6) * 11.0F;
          ++f6;
      }

      f6 = 0.47123894F + par3 * (float)Math.PI * -0.05F;

      for (i = 8; i < 12; ++i)
      {
          this.Sticks[i].rotationPointY = 4.0F + MathHelper.cos(((float)i * 1.5F + par3) * 0.5F);
          this.Sticks[i].rotationPointX = MathHelper.cos(f6) * 13.0F;
          this.Sticks[i].rotationPointZ = MathHelper.sin(f6) * 13.0F;
          ++f6;
      }

      this.Head.rotateAngleY = par4 / (180F / (float)Math.PI);
      this.Head.rotateAngleX = par5 / (180F / (float)Math.PI);
  }

}