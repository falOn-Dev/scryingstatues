package io.github.falon.scryingstatues.entity.statue;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StatueRenderer extends GeoEntityRenderer<StatueEntity> {
	public StatueRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new StatueModel());
	}
}
