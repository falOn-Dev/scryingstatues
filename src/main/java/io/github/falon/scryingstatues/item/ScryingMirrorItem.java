package io.github.falon.scryingstatues.item;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class ScryingMirrorItem extends Item {
	public ScryingMirrorItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		NbtCompound nbt = stack.getOrCreateNbt();

		if(nbt.contains("target")) {
			if(user.isSneaking()) {
				nbt.remove("target");
				user.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.clear_message"), true);
			} else {
				UUID target = nbt.getUuid("target");
				user.sendMessage(Text.of(target.toString()), true);
			}
			return TypedActionResult.success(stack);
		}

		return TypedActionResult.pass(stack);
	}
}
