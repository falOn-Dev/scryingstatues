package io.github.falon.scryingstatues.init

import io.github.falon.scryingstatues.ScryingStatues
import io.github.falon.scryingstatues.item.ScryingMirrorItem
import io.github.falon.scryingstatues.item.ShattererItem
import io.github.falon.scryingstatues.item.StatueItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import java.util.function.Consumer

object ModItems {
    val items: MutableMap<Item, Identifier> = LinkedHashMap()

    val STATUE: Item = makeItem("statue", StatueItem(QuiltItemSettings().maxCount(1)))
    val SHATTERER: Item = makeItem("shatterer", ShattererItem(QuiltItemSettings().maxCount(1).maxDamage(128)))
    val SCRYING_MIRROR: Item = makeItem("scrying_mirror", ScryingMirrorItem(QuiltItemSettings().maxCount(1)))

    private fun <T : Item> makeItem(name: String, item: T): T {
        items[item] = Identifier(ScryingStatues.MOD_ID, name)
        return item
    }

    init {
        items.keys.forEach(
            Consumer { item: Item ->
                Registry.register(
                    Registries.ITEM,
                    items[item],
                    item,
                )
            },
        )

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS_AND_UTILITIES).register { entries ->
            items.keys.forEach {
                entries.addItem(it)
            }
        }
    }
}
