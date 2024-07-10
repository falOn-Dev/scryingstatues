package io.github.falon.scryingstatues.providers

import io.github.falon.scryingstatues.init.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Items
import net.minecraft.recipe.RecipeCategory
import net.minecraft.util.Identifier
import java.util.function.Consumer

class RecipeProvider(output: FabricDataOutput?) : FabricRecipeProvider(output) {
    override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>) {
        ShapedRecipeJsonFactory.create(RecipeCategory.DECORATIONS, ModItems.STATUE, 1)
            .pattern("SES")
            .pattern("SAS")
            .pattern("SSS")
            .ingredient('S', Items.STONE)
            .ingredient('E', Items.ENDER_EYE)
            .ingredient('A', Items.ARMOR_STAND)
            .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
            .criterion(hasItem(Items.ARMOR_STAND), conditionsFromItem(Items.ARMOR_STAND))
            .offerTo(exporter, Identifier(getRecipeName(ModItems.STATUE)))

        ShapedRecipeJsonFactory.create(RecipeCategory.TOOLS, ModItems.SHATTERER, 1)
            .pattern("  I")
            .pattern(" I ")
            .pattern("S  ")
            .ingredient('S', Items.STICK)
            .ingredient('I', Items.IRON_INGOT)
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
            .offerTo(exporter, Identifier(getRecipeName(ModItems.SHATTERER)))
    }
}
