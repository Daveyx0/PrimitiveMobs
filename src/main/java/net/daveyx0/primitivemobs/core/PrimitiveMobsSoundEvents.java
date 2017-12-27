package net.daveyx0.primitivemobs.core;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(PrimitiveMobsReference.MODID)
public class PrimitiveMobsSoundEvents 
{
	
	@ObjectHolder("entity.brainslime.slimecharge")
	public static final SoundEvent ENTITY_BRAINSLIME_CHARGE = createSoundEvent("entity.brainslime.slimecharge");
	
	@ObjectHolder("entity.motherspider.spiderscreech")
	public static final SoundEvent ENTITY_MOTHERSPIDER_SCREECH = createSoundEvent("entity.motherspider.spiderscreech");
	
	@ObjectHolder("entity.grovesprite.angry")
	public static final SoundEvent ENTITY_GROVESPRITE_ANGRY = createSoundEvent("entity.grovesprite.angry");
	
	@ObjectHolder("entity.grovesprite.death")
	public static final SoundEvent ENTITY_GROVESPRITE_DEATH = createSoundEvent("entity.grovesprite.death");
	
	@ObjectHolder("entity.grovesprite.hurt")
	public static final SoundEvent ENTITY_GROVESPRITE_HURT = createSoundEvent("entity.grovesprite.hurt");
	
	@ObjectHolder("entity.grovesprite.idle")
	public static final SoundEvent ENTITY_GROVESPRITE_IDLE = createSoundEvent("entity.grovesprite.idle");
	
	@ObjectHolder("entity.grovesprite.thanks")
	public static final SoundEvent ENTITY_GROVESPRITE_THANKS = createSoundEvent("entity.grovesprite.thanks");
	
	@ObjectHolder("entity.trollager.idle")
	public static final SoundEvent ENTITY_TROLLAGER_IDLE = createSoundEvent("entity.trollager.idle");
	
	@ObjectHolder("entity.trollager.hit")
	public static final SoundEvent ENTITY_TROLLAGER_HIT = createSoundEvent("entity.trollager.hit");
	
	@ObjectHolder("entity.trollager.death")
	public static final SoundEvent ENTITY_TROLLAGER_DEATH = createSoundEvent("entity.trollager.death");
	
	@ObjectHolder("entity.trollager.attack")
	public static final SoundEvent ENTITY_TROLLAGER_ATTACK = createSoundEvent("entity.trollager.attack");

	
	private static SoundEvent createSoundEvent(final String soundName) {
		final ResourceLocation soundID = new ResourceLocation(PrimitiveMobsReference.MODID, soundName);
		return new SoundEvent(soundID).setRegistryName(soundID);
	}

	@Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(
					ENTITY_BRAINSLIME_CHARGE ,
					ENTITY_MOTHERSPIDER_SCREECH,
					ENTITY_GROVESPRITE_ANGRY,
					ENTITY_GROVESPRITE_DEATH,
					ENTITY_GROVESPRITE_HURT,
					ENTITY_GROVESPRITE_IDLE,
					ENTITY_GROVESPRITE_THANKS,
					ENTITY_TROLLAGER_IDLE,
					ENTITY_TROLLAGER_HIT,
					ENTITY_TROLLAGER_DEATH,
					ENTITY_TROLLAGER_ATTACK
			);
		}
	}
}
