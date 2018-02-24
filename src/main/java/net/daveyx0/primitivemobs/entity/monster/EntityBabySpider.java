package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.entity.ai.EntityAICustomFollowOwner;
import net.daveyx0.primitivemobs.entity.ai.EntityAICustomOwnerHurtByTarget;
import net.daveyx0.primitivemobs.entity.ai.EntityAICustomOwnerHurtTarget;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBabySpider extends EntityPrimitiveSpider 
{
    private int rideAttemptDelay = rand.nextInt(100);
    boolean initChild = false;
    
	public EntityBabySpider(World worldIn) {
		super(worldIn);
		this.setTamed(false);
		this.setSize(0.7F, 0.45F);
	}
	
    protected void entityInit()
    {
        super.entityInit();
    }
   
    protected void initEntityAI()
    {	
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityPrimitiveSpider.AISpiderAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAICustomFollowOwner(this, 1.0D, 8.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAICustomOwnerHurtByTarget(this));
        this.targetTasks.addTask(3, new EntityAICustomOwnerHurtTarget(this));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return /*source.getDamageType().equals("inWall") ? false : */ super.attackEntityFrom(source, amount);
    }
    
	public void onUpdate()
	{
		super.onUpdate();
        if(!initChild && this.getOwner() != null && this.getOwner() instanceof EntityMotherSpider)
        {
        	EntityMotherSpider mother = (EntityMotherSpider)this.getOwner();
        	mother.addFollower(this);
        	initChild = true;
        }
        
		if(rideAttemptDelay++ > 100 && this.getOwner() != null && !this.getOwner().isBeingRidden())
		{
			this.startRiding(this.getOwner());
		}
		
        if(getEntityWorld().isRemote && this.getOwner() == null)
        {
        	getEntityWorld().spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + (rand.nextFloat() - rand.nextFloat()), posY + (rand.nextFloat() - rand.nextFloat()) + 1F, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
        }
	}

    
}
