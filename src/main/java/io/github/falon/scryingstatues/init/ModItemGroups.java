package io.github.falon.scryingstatues.init;

import io.github.falon.scryingstatues.ScryingStatues;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface ModItemGroups {
	ItemGroup SCRYING_STATUES_GROUP = FabricItemGroup.builder()
		.icon(() -> ModItems.SCRYING_MIRROR.asItem().getDefaultStack())
		.name(Text.translatable("itemGroup.scryingstatues.scrying_statues_group"))
		.entries((context, entries) -> {
			ModItems.items.forEach((item, identifier) -> entries.addItem(item));
		})
		.build();

	static void initialize() {
		Registry.register(Registries.ITEM_GROUP, new Identifier(ScryingStatues.MOD_ID, "scrying_statues_group"), SCRYING_STATUES_GROUP);
	}
}
