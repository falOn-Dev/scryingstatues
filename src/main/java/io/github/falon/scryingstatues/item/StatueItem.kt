package io.github.falon.scryingstatues.item

import io.github.falon.scryingstatues.ScryingStatues.Companion.LOGGER
import io.github.falon.scryingstatues.entity.statue.StatueEntity
import io.github.falon.scryingstatues.init.ModEntities
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class StatueItem(settings: Settings?) : Item(settings) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val player = context.player
        val stack = player!!.mainHandStack

        if (player.isSneaking) {
            spawnStatue(context.world, context.blockPos, player, stack, stack.name.string)
            LOGGER.info("Spawning statue at {}", context.blockPos.toString())
            LOGGER.info("Player yaw: {}", player.yaw)
            stack.decrement(1)
            return ActionResult.SUCCESS
        }

        return ActionResult.PASS
    }

    private fun spawnStatue(world: World, blockPos: BlockPos, player: PlayerEntity?, stack: ItemStack, name: String) {
        val statue = StatueEntity(ModEntities.STATUE, world)
        statue.refreshPositionAndAngles(blockPos.up(1), player!!.yaw + 180, 0f)
        statue.setOwner(player.uuid)
        statue.statueName = name

        world.spawnEntity(statue)
    }
}
