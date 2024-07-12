package io.github.falon.scryingstatues

import io.github.falon.scryingstatues.init.ModEntities
import io.github.falon.scryingstatues.init.ModItemGroups
import io.github.falon.scryingstatues.init.ModItems
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ScryingStatues : ModInitializer {
    override fun onInitialize(mod: ModContainer) {
        LOGGER.info("Hello Quilt world from {}! Stay fresh!", mod.metadata().name())

        ModEntities
        ModItems
        ModItemGroups
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger("Scrying Statues")
        const val MOD_ID: String = "scryingstatues"
    }
}
