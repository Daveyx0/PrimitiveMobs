package net.daveyx0.primitivemobs.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySwimmingCreature extends EntityCreature {

public EntitySwimmingCreature(World worldIn) {
		super(worldIn);
		this.moveHelper = new SwimmingMoveHelper();
	}

/**
 * Called when the entity is attacked.
 */
public boolean attackEntityFrom(DamageSource source, float amount)
{
    return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
}

public boolean attackEntityAsMob(Entity entityIn)
{
    float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    int i = 0;

    if (entityIn instanceof EntityLivingBase)
    {
        f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
        i += EnchantmentHelper.getKnockbackModifier(this);
    }

    boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

    if (flag)
    {
        if (i > 0 && entityIn instanceof EntityLivingBase)
        {
            ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
            this.motionX *= 0.6D;
            this.motionZ *= 0.6D;
        }

        int j = EnchantmentHelper.getFireAspectModifier(this);

        if (j > 0)
        {
            entityIn.setFire(j * 4);
        }

        if (entityIn instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityIn;
            ItemStack itemstack = this.getHeldItemMainhand();
            ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

            if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() instanceof ItemShield)
            {
                float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                if (this.rand.nextFloat() < f1)
                {
                    entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                    this.getEntityWorld().setEntityState(entityplayer, (byte)30);
                }
            }
        }

        this.applyEnchantments(this, entityIn);
    }

    return flag;
}

protected void applyEntityAttributes()
{
    super.applyEntityAttributes();
    this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
}

@Override
protected PathNavigate createNavigator(World worldIn)
{
    return new PathNavigateSwimmer(this, worldIn);
}

class SwimmingMoveHelper extends EntityMoveHelper
{
    private EntitySwimmingCreature swimmingEntity = EntitySwimmingCreature.this;

    public SwimmingMoveHelper()
    {
        super(EntitySwimmingCreature.this);
    }

    @Override
    public void onUpdateMoveHelper()
    {
        if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.swimmingEntity.getNavigator().noPath())
        {
            double d0 = this.posX - this.swimmingEntity.posX;
            double d1 = this.posY - this.swimmingEntity.posY;
            double d2 = this.posZ - this.swimmingEntity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = (double)MathHelper.sqrt(d3);
            d1 /= d3;
            float f = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.swimmingEntity.rotationYaw = this.limitAngle(this.swimmingEntity.rotationYaw, f, 30.0F);
            this.swimmingEntity.setAIMoveSpeed((float)(this.speed * this.swimmingEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
            this.swimmingEntity.motionY += (double)this.swimmingEntity.getAIMoveSpeed() * d1 * 0.1D;
        }
        else
        {
            this.swimmingEntity.setAIMoveSpeed(0.0F);
        }
    }
}

}