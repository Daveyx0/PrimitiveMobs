package net.daveyx0.primitivemobs.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PrimitiveMobsSoundEvents 
{
	
	public static final SoundEvent ENTITY_BRAINSLIME_CHARGE = createSoundEvent("entity.brainslime.slimecharge");
	
	public static final SoundEvent ENTITY_MOTHERSPIDER_SCREECH = createSoundEvent("entity.motherspider.spiderscreech");
	
	public static final SoundEvent ENTITY_GROVESPRITE_ANGRY = createSoundEvent("entity.grovesprite.angry");

	public static final SoundEvent ENTITY_GROVESPRITE_DEATH = createSoundEvent("entity.grovesprite.death");
	
	public static final SoundEvent ENTITY_GROVESPRITE_HURT = createSoundEvent("entity.grovesprite.hurt");
	
	public static final SoundEvent ENTITY_GROVESPRITE_IDLE = createSoundEvent("entity.grovesprite.idle");
	
	public static final SoundEvent ENTITY_GROVESPRITE_THANKS = createSoundEvent("entity.grovesprite.thanks");
	
	public static final SoundEvent ENTITY_TROLLAGER_IDLE = createSoundEvent("entity.trollager.idle");
	
	public static final SoundEvent ENTITY_TROLLAGER_HIT = createSoundEvent("entity.trollager.hit");

	public static final SoundEvent ENTITY_TROLLAGER_DEATH = createSoundEvent("entity.trollager.death");

	public static final SoundEvent ENTITY_TROLLAGER_ATTACK = createSoundEvent("entity.trollager.attack");
	
	public static final SoundEvent ENTITY_HARPY_IDLE = createSoundEvent("entity.harpy.idle");
	
	public static final SoundEvent ENTITY_HARPY_HURT = createSoundEvent("entity.harpy.hurt");
	
	public static final SoundEvent ENTITY_FLAMESPEWER_IDLE = createSoundEvent("entity.flamespewer.idle");
	
	public static final SoundEvent ENTITY_VOIDEYE_IDLE = createSoundEvent("entity.voideye.idle");

	
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
					ENTITY_TROLLAGER_ATTACK,
					ENTITY_HARPY_IDLE,
					ENTITY_HARPY_HURT,
					ENTITY_FLAMESPEWER_IDLE,
					ENTITY_VOIDEYE_IDLE
			);
		}
	}
}
