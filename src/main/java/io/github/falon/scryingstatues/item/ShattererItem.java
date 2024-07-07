package io.github.falon.scryingstatues.item;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.entity.statue.StatueEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ShattererItem extends Item {
	public ShattererItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		ScryingStatues.LOGGER.info("Shattering entity: {}", entity.toString());

		return ActionResult.SUCCESS;
	}
}
