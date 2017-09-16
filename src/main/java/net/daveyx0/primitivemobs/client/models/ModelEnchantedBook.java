package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnchantedBook extends ModelBase
{
    ModelRenderer Book1;
    ModelRenderer Book2;
    ModelRenderer Pages1;
    ModelRenderer Pages2;
    ModelRenderer Page1;
    ModelRenderer Page2;
  
  public ModelEnchantedBook()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Book1 = new ModelRenderer(this, 0, 0);
      Book1.addBox(-6F, -5F, 0F, 6, 10, 0);
      Book1.setRotationPoint(0F, 20F, 0F);
      Book1.setTextureSize(64, 32);
      Book1.mirror = true;
      setRotation(Book1, 1.570796F, 0F, 0F);
      Book2 = new ModelRenderer(this, 16, 0);
      Book2.addBox(0F, -5F, 0F, 6, 10, 0);
      Book2.setRotationPoint(0F, 20F, 0F);
      Book2.setTextureSize(64, 32);
      Book2.mirror = true;
      setRotation(Book2, 1.570796F, 0F, 0F);
      Pages1 = new ModelRenderer(this, 0, 10);
      Pages1.addBox(-5F, -4F, -1.1F, 5, 8, 1);
      Pages1.setRotationPoint(0F, 20F, 0F);
      Pages1.setTextureSize(64, 32);
      Pages1.mirror = true;
      setRotation(Pages1, 1.570796F, 0F, 0F);
      Pages2 = new ModelRenderer(this, 12, 10);
      Pages2.addBox(0F, -4F, -1.1F, 5, 8, 1);
      Pages2.setRotationPoint(0F, 20F, 0F);
      Pages2.setTextureSize(64, 32);
      Pages2.mirror = true;
      setRotation(Pages2, 1.570796F, 0F, 0F);
      Page1 = new ModelRenderer(this, 24, 10);
      Page1.addBox(0F, -4F, -1.1F, 5, 8, 0);
      Page1.setRotationPoint(0F, 20F, 0F);
      Page1.setTextureSize(64, 32);
      Page1.mirror = true;
      setRotation(Page1, 1.570796F, 0F, 0.5235988F);
      Page2 = new ModelRenderer(this, 24, 10);
      Page2.addBox(-5F, -4F, -1.1F, 5, 8, 0);
      Page2.setRotationPoint(0F, 20F, 0F);
      Page2.setTextureSize(64, 32);
      Page2.mirror = true;
      setRotation(Page2, 1.570796F, 0F, -0.5235988F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Book1.render(f5);
    Book2.render(f5);
    Pages1.render(f5);
    Pages2.render(f5);
    Page1.render(f5);
    Page2.render(f5);
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
    Book1.rotateAngleZ = f2;
    Book2.rotateAngleZ = -f2;
    Pages1.rotateAngleZ = f2;
    Pages2.rotateAngleZ = -f2;
    Page1.rotateAngleZ = f2 + 0.5235988F;
    Page2.rotateAngleZ = -f2 - 0.5235988F;
  }

}
