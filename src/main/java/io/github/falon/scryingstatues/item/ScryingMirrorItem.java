package io.github.falon.scryingstatues.item;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.entity.statue.StatueEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
//		boolean isScrying = false;
//
//		if(entity instanceof ServerPlayerEntity player) {
//			isScrying = player.getCameraEntity() instanceof StatueEntity;
//		}
//
//		ScryingStatues.LOGGER.info("isScrying: " + isScrying);
//
//		if(world.isClient())
//
//		super.inventoryTick(stack, world, entity, slot, selected);
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
				if (user instanceof ServerPlayerEntity player) {
					UUID target = nbt.getUuid("target");
					if (world instanceof ServerWorld serverWorld) {
						StatueEntity targetStatue = (StatueEntity) serverWorld.getEntity(target);
						Vec3d pose = player.getPos();
						player.setCameraEntity(targetStatue);

					}
				}
			}
			return TypedActionResult.success(stack);
		}

		return TypedActionResult.pass(stack);
	}
}
