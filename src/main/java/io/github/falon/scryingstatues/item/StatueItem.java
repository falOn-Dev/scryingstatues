package io.github.falon.scryingstatues.item;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.entity.statue.StatueEntity;
import io.github.falon.scryingstatues.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StatueItem extends Item {

	public StatueItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		ItemStack stack = player.getMainHandStack();

		if (player.isSneaking()) {
			spawnStatue(context.getWorld(), context.getBlockPos(), player, stack);
			ScryingStatues.LOGGER.info("Spawning statue at {}", context.getBlockPos().toString());
			ScryingStatues.LOGGER.info("Player yaw: {}", player.getYaw());
			stack.decrement(1);
			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;

	}

	private void spawnStatue(World world, BlockPos blockPos, PlayerEntity player, ItemStack stack) {
		StatueEntity statue = new StatueEntity(ModEntities.STATUE, world);
		statue.refreshPositionAndAngles(blockPos.up(1), player.getYaw() + 180, 0);
		statue.setOwner(player.getUuid());

		world.spawnEntity(statue);
	}
}
