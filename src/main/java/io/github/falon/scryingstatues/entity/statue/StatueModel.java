package io.github.falon.scryingstatues.entity.statue;

import io.github.falon.scryingstatues.ScryingStatues;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class StatueModel extends GeoModel<StatueEntity> {
	public static final Identifier MODEL = new Identifier(ScryingStatues.MOD_ID, "geo/statue/statue.geo.json");
	public static final Identifier TEXTURE = new Identifier(ScryingStatues.MOD_ID, "textures/entity/statue/statue.png");
	public static final Identifier ANIMATION = new Identifier(ScryingStatues.MOD_ID, "animations/statue/statue.animation.json");

	@Override
	public Identifier getModelResource(StatueEntity animatable) {
		return MODEL;
	}

	@Override
	public Identifier getTextureResource(StatueEntity animatable) {
		return TEXTURE;
	}

	@Override
	public Identifier getAnimationResource(StatueEntity animatable) {
		return ANIMATION;
	}
}
