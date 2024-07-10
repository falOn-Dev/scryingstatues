package io.github.falon.scryingstatues.item;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.entity.statue.StatueEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class ScryingMirrorItem extends Item {
	public ScryingMirrorItem(Settings settings) {
		super(settings);
	}


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		NbtCompound nbt = stack.getOrCreateNbt();

		if (nbt.contains("target")) {
			if (user.isSneaking()) {
				nbt.remove("target");
				user.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.clear_message"), true);
			} else {
				if (nbt.contains("target")) {
					if (user instanceof ServerPlayerEntity player) {
						UUID target = nbt.getUuid("target");
						if (world instanceof ServerWorld serverWorld) {
							StatueEntity targetStatue = (StatueEntity) serverWorld.getEntity(target);
							if (targetStatue.isScryable()) {
								Vec3d pose = player.getPos();
								player.setCameraEntity(targetStatue);
							} else {
								player.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.unreachable"), true);
							}
						}
					}
				}
			}
			return TypedActionResult.success(stack);
		}

		return TypedActionResult.pass(stack);
	}
}
