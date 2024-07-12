package io.github.falon.scryingstatues.providers

import io.github.falon.scryingstatues.init.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.Models

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {}

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(ModItems.SHATTERER, Models.SINGLE_LAYER_ITEM)
        itemModelGenerator.register(ModItems.SCRYING_MIRROR, Models.HANDHELD)
        itemModelGenerator.register(ModItems.STATUE, Models.SINGLE_LAYER_ITEM)
    }
}
