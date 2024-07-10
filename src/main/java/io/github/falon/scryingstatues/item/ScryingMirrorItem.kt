package io.github.falon.scryingstatues.item

import io.github.falon.scryingstatues.entity.statue.StatueEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class ScryingMirrorItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        val nbt = stack.getOrCreateNbt()

        if (nbt.contains("target")) {
            if (user.isSneaking) {
                nbt.remove("target")
                user.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.clear_message"), true)
            } else {
                if (nbt.contains("target")) {
                    if (user is ServerPlayerEntity) {
                        val target = nbt.getUuid("target")
                        if (world is ServerWorld) {
                            val targetStatue = world.getEntity(target) as StatueEntity?
                            if (targetStatue!!.isScryable) {
                                val pose = user.getPos()
                                user.cameraEntity = targetStatue
                            } else {
                                user.sendMessage(
                                    Text.translatable("item.scryingstatues.scrying_mirror.unreachable"),
                                    true,
                                )
                            }
                        }
                    }
                }
            }
            return TypedActionResult.success(stack)
        }

        return TypedActionResult.pass(stack)
    }
}
