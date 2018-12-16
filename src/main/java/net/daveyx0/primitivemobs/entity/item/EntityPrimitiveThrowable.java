package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveCreeper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPrimitiveThrowable extends EntityThrowable{

	public Class<? extends EntityLiving> spawnEntityClass;
	int spawnChance;
	EntityLivingBase thrower = null;
	
	public EntityPrimitiveThrowable(World worldIn){
		super(worldIn);
		spawnEntityClass = EntityChicken.class;
		this.spawnChance = 0;
	}
	
	public EntityPrimitiveThrowable(World worldIn, int chance){
		super(worldIn);
		spawnEntityClass = EntityChicken.class;
		this.spawnChance = chance;
	}
	
	  public EntityPrimitiveThrowable(World worldIn, Class<? extends EntityLiving> entity, int chance) {
		super(worldIn);
		this.spawnEntityClass = entity;
		this.spawnChance = chance;
	}
	  
	    public EntityPrimitiveThrowable(World worldIn, EntityLivingBase throwerIn, Class<? extends EntityLiving> entity, int chance)
	    {
	        super(worldIn, throwerIn);
	        this.spawnEntityClass = entity;
	        this.spawnChance = chance;
	        this.thrower = throwerIn;
	    }

	    public EntityPrimitiveThrowable(World worldIn, double x, double y, double z, Class<? extends EntityLiving> entity, int chance)
	    {
	        super(worldIn, x, y, z);
	        this.spawnEntityClass = entity;
	        this.spawnChance = chance;
	    }


	/**
     * Called when this EntityThrowable hits a block or entity.
     */
	@Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        if (!this.world.isRemote)
        {
            if (this.rand.nextInt(spawnChance) == 0)
            {
                int i = 1;

                for (int j = 0; j < i; ++j)
                {
                	EntityLiving entity = (EntityLiving)EntityRegistry.getEntry(spawnEntityClass).newInstance(world);
                	if(entity instanceof EntityAnimal)
                	{
                		EntityAnimal animal = (EntityAnimal)entity;
                		animal.setGrowingAge(-24000);
                    	animal.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                        this.world.spawnEntity(animal);
                	}
                	else if(entity instanceof EntityPrimitiveCreeper)
                	{
                		EntityPrimitiveCreeper creeper = (EntityPrimitiveCreeper)entity;
                		creeper.setGrowingAge(-24000);
                		creeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        				ITameableEntity tameable = EntityUtil.getCapability(entity, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        				if(tameable != null && !tameable.isTamed() && thrower != null)
        				{
        					CapabilityTameableEntity.EventHandler.setUpTameable(tameable, entity, thrower);
        				}
                        this.world.spawnEntity(creeper);
                	}
                	else
                	{
                    	entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        				ITameableEntity tameable = EntityUtil.getCapability(entity, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        				if(tameable != null && !tameable.isTamed() && thrower != null)
        				{
        					CapabilityTameableEntity.EventHandler.setUpTameable(tameable, entity, thrower);
        				}
                        this.world.spawnEntity(entity);
                	}
                }
            }

            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
	
	 public static void registerFixesEgg(DataFixer fixer)
	    {
	        EntityThrowable.registerFixesThrowable(fixer, "ThrownEgg");
	    }

	    /**
	     * Handler for {@link World#setEntityState}
	     */
	    @SideOnly(Side.CLIENT)
	    public void handleStatusUpdate(byte id)
	    {
	        if (id == 3)
	        {
	            double d0 = 0.08D;

	            for (int i = 0; i < 8; ++i)
	            {
	                this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(Items.EGG));
	            }
	        }
	    }

		public ItemStack getItemFromEntity() {

			return new ItemStack(Items.EGG);
		}


}
