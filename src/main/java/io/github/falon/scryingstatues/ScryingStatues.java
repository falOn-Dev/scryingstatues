package io.github.falon.scryingstatues;

import io.github.falon.scryingstatues.init.ModEntities;
import io.github.falon.scryingstatues.init.ModItemGroups;
import io.github.falon.scryingstatues.init.ModItems;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScryingStatues implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Scrying Statues");
	public static final String MOD_ID = "scryingstatues";

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hello Quilt world from {}! Stay fresh!", mod.metadata().name());
		ModEntities.initialize();
		ModItems.initialize();
		ModItemGroups.initialize();
    }
}
