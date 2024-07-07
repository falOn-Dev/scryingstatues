package io.github.falon.scryingstatues.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class StatueItem extends Item {

	public StatueItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return super.useOnBlock(context);
	}
}
