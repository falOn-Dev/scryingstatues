package io.github.falon.scryingstatues

import io.github.falon.scryingstatues.entity.statue.StatueRenderer
import io.github.falon.scryingstatues.init.ModEntities
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer

class ScryingStatuesClient : ClientModInitializer {
    override fun onInitializeClient(mod: ModContainer?) {
        EntityRendererRegistry.register(ModEntities.STATUE, ::StatueRenderer)
    }
}
