package io.github.falon.scryingstatues.init;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.entity.statue.StatueEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public interface ModEntities {

	EntityType<StatueEntity> STATUE = Registry.register(Registries.ENTITY_TYPE, new Identifier(ScryingStatues.MOD_ID, "statue"),
		QuiltEntityTypeBuilder.create(SpawnGroup.MISC, StatueEntity::new).setDimensions(EntityDimensions.fixed(0.5F, 1.975F)).build());

	public static void initialize() {
		ScryingStatues.LOGGER.info("Initializing entities");
	}
}
