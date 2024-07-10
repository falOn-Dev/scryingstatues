package io.github.falon.scryingstatues.init

import io.github.falon.scryingstatues.ScryingStatues
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroup.DisplayParameters
import net.minecraft.item.ItemGroup.ItemStackCollector
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object ModItemGroups {
    val SCRYING_STATUES_GROUP: ItemGroup = FabricItemGroup.builder()
        .icon { ModItems.SCRYING_MIRROR.asItem().defaultStack }
        .name(Text.translatable("itemGroup.scryingstatues.scrying_statues_group"))
        .entries { context: DisplayParameters?, entries: ItemStackCollector ->
            ModItems.items.forEach { (item: Item?, identifier: Identifier?) ->
                entries.addItem(
                    item,
                )
            }
        }
        .build()

    init {
        Registry.register(
            Registries.ITEM_GROUP,
            Identifier(ScryingStatues.MOD_ID, "scrying_statues_group"),
            SCRYING_STATUES_GROUP,
        )
    }
}
