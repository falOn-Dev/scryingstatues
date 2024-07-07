package io.github.falon.scryingstatues;

import io.github.falon.scryingstatues.entity.statue.StatueRenderer;
import io.github.falon.scryingstatues.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ScryingStatuesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This code runs on the client side. However, since we don't have any client-side code yet, this method is empty.
		EntityRendererRegistry.register(ModEntities.STATUE, StatueRenderer::new);
	}
}
