package io.github.falon.scryingstatues.item

import io.github.falon.scryingstatues.entity.statue.StatueEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import java.util.*

class ScryingMirrorItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        val nbt = stack.getOrCreateNbt()

        if (nbt.contains("target")) {
            if (user.isSneaking) {
                nbt.remove("target")
                user.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.clear_message"), true)
                user.playSound(SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP)
            } else {
                if (nbt.contains("target")) {
                    if (user is ServerPlayerEntity) {
                        val target = nbt.getUuid("target")
                        if (world is ServerWorld) {
                            val targetStatue = world.getEntity(target) as StatueEntity?
                            if (targetStatue!!.isScryable) {
                                val pose = user.getPos()
                                user.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f)
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

    override fun getName(stack: ItemStack?): Text {
        val nbt = stack!!.getOrCreateNbt()
        if (nbt.contains("target")) {
            return super.getName(stack).copy().append(Text.of(" (${nbt.getString("display_name")})"))
        } else {
            return super.getName(stack)
        }
    }
}
