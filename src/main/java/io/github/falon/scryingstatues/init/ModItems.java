package io.github.falon.scryingstatues.init;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.item.ScryingMirrorItem;
import io.github.falon.scryingstatues.item.ShattererItem;
import io.github.falon.scryingstatues.item.StatueItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModItems {
	Map<Item, Identifier> items = new LinkedHashMap<>();

	Item STATUE = makeItem("statue", new StatueItem(new QuiltItemSettings().maxCount(1)));
	Item SHATTERER = makeItem("shatterer", new ShattererItem(new QuiltItemSettings().maxCount(1).maxDamage(128)));
	Item SCRYING_MIRROR = makeItem("scrying_mirror", new ScryingMirrorItem(new QuiltItemSettings().maxCount(1)));


	private static <T extends Item> T makeItem(String name, T item) {
		items.put(item, new Identifier(ScryingStatues.MOD_ID, name));
		return item;
	}

	static void initialize() {
		items.keySet().forEach(item -> {
			Registry.register(Registries.ITEM, items.get(item), item);
		});


	}
}
