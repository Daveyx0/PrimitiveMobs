package net.daveyx0.primitivemobs.core;

import net.daveyx0.multimob.core.MMTameableEntries;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class PrimitiveTameableEntries extends MMTameableEntries {

	public static void registerTameables()
	{
		addTameable(EntityChameleon.class, new Item[]{Items.MELON}, new Item[]{Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE}, 20f, 100, true);
		addTameable(EntityBabySpider.class, new Item[]{Items.MELON}, new Item[]{Items.ROTTEN_FLESH}, 20f, 100, false);
		addTameable(EntityFestiveCreeper.class, new Item[]{Items.MELON}, new Item[]{Items.GUNPOWDER}, 20f, 100, false);
		addTameable(EntitySupportCreeper.class, new Item[]{Items.MELON}, new Item[]{Items.GUNPOWDER}, 20f, 100, false);
		addTameable(EntityRocketCreeper.class, new Item[]{Items.MELON}, new Item[]{Items.GUNPOWDER}, 20f, 100, false);
	}
}
