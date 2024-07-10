package io.github.falon.scryingstatues.init

import io.github.falon.scryingstatues.ScryingStatues
import io.github.falon.scryingstatues.entity.statue.StatueEntity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder

object ModEntities {
    val STATUE: EntityType<StatueEntity?> =
        Registry.register(
            Registries.ENTITY_TYPE,
            Identifier(ScryingStatues.MOD_ID, "statue"),
            QuiltEntityTypeBuilder.create(
                SpawnGroup.MISC,
            ) { variant: EntityType<StatueEntity?>?, world: World? ->
                StatueEntity(
                    variant,
                    world,
                )
            }.setDimensions(EntityDimensions.fixed(0.5f, 1.975f)).build(),
        )
}
