package io.github.falon.scryingstatues.item

import io.github.falon.scryingstatues.ScryingStatues.Companion.LOGGER
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

class ShattererItem(settings: Settings?) : Item(settings) {
    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        LOGGER.info("Shattering entity: {}", entity.toString())

        return ActionResult.SUCCESS
    }
}
