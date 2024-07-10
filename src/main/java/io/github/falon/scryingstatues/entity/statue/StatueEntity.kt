package io.github.falon.scryingstatues.entity.statue

import io.github.falon.scryingstatues.ScryingStatues
import io.github.falon.scryingstatues.item.ScryingMirrorItem
import io.github.falon.scryingstatues.item.ShattererItem
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar
import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.animation.AnimationState
import software.bernie.geckolib.core.animation.RawAnimation
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.*

class StatueEntity(variant: EntityType<*>?, world: World?) : Entity(variant, world), GeoEntity {
    private val geoCache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    init {
        this.inanimate = true
    }

    override fun collides(): Boolean {
        return true
    }

    override fun isCollidable(): Boolean {
        return true
    }

    override fun interact(player: PlayerEntity, hand: Hand): ActionResult {
        val stack = player.getStackInHand(hand)
        ScryingStatues.LOGGER.info("isScryable: {}", isScryable)
        if (stack.item is ShattererItem) {
            player.playSound(SoundEvents.BLOCK_STONE_PLACE, 1.0f, 1.0f)

            ScryingStatues.LOGGER.info("Shattering entity: {}", this.toString())

            getDataTracker().set(HEALTH, getDataTracker().get(HEALTH) - 1)
            world.addBlockBreakParticles(BlockPos.create(this.x, this.y, this.z), Blocks.STONE.defaultState)

            stack.damage(
                1,
                player,
            ) { playerEntity: PlayerEntity ->
                playerEntity.sendToolBreakStatus(hand)
            }

            if (getDataTracker().get(HEALTH) <= 0) {
                this.remove(RemovalReason.KILLED)
                this.dropStack(ItemStack(Items.STONE, 3))
            }

            return ActionResult.SUCCESS
        } else if (stack.item is ScryingMirrorItem) {
            if (owner.isPresent && player.isSneaking) {
                stack.getOrCreateNbt().putUuid("target", this.getUuid())
                player.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.set_message"), true)

                return ActionResult.SUCCESS
            }
        }
        return ActionResult.PASS
    }

    override fun initDataTracker() {
        getDataTracker().startTracking(HEALTH, 3)
        getDataTracker().startTracking(OWNER, Optional.empty())
        getDataTracker().startTracking(SCRYABLE, true)
        getDataTracker().startTracking(STATUE_NAME, "Statue")
    }

    var isScryable: Boolean
        get() = getDataTracker().get(SCRYABLE)
        set(scryable) {
            getDataTracker().set(SCRYABLE, scryable)
        }

    override fun tick() {
        isScryable = world
            .isAir(BlockPos.create(this.x, this.y + 1, this.z))

        super.tick()
    }

    val owner: Optional<UUID>
        get() = getDataTracker().get(OWNER)

    fun setOwner(owner: UUID) {
        getDataTracker().set(OWNER, Optional.of(owner))
    }

    var statueName: String
        get() = getDataTracker().get(STATUE_NAME)
        set(displayName) {
            getDataTracker().set(STATUE_NAME, displayName)
        }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        if (nbt.contains("health")) {
            getDataTracker().set(HEALTH, nbt.getInt("health"))
        }

        if (nbt.contains("owner")) {
            getDataTracker().set(OWNER, Optional.of(nbt.getUuid("owner")))
        }

        if (nbt.contains("scryable")) {
            getDataTracker().set(SCRYABLE, nbt.getBoolean("scryable"))
        }

        if (nbt.contains("display_name")) {
            getDataTracker().set(STATUE_NAME, nbt.getString("display_name"))
        }
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        nbt.putInt("health", getDataTracker().get(HEALTH))
        getDataTracker().get(OWNER).ifPresent { uuid: UUID? ->
            nbt.putUuid(
                "owner",
                uuid,
            )
        }
        nbt.putBoolean("scryable", getDataTracker().get(SCRYABLE))
        nbt.putString("display_name", getDataTracker().get(STATUE_NAME))
    }

    override fun registerControllers(controllers: ControllerRegistrar) {
        controllers.add(
            AnimationController(
                this,
                "controller",
                20,
            ) { statueEntityAnimationState: AnimationState<StatueEntity> ->
                this.predicate(
                    statueEntityAnimationState,
                )
            },
        )
    }

    private fun predicate(statueEntityAnimationState: AnimationState<StatueEntity>): PlayState {
        return statueEntityAnimationState.setAndContinue(DEFAULT_POSE)
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return this.geoCache
    }

    companion object {
        private val HEALTH: TrackedData<Int> =
            DataTracker.registerData(StatueEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        private val OWNER: TrackedData<Optional<UUID>> = DataTracker.registerData(
            StatueEntity::class.java,
            TrackedDataHandlerRegistry.OPTIONAL_UUID,
        )
        private val SCRYABLE: TrackedData<Boolean> =
            DataTracker.registerData(StatueEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        private val STATUE_NAME: TrackedData<String> =
            DataTracker.registerData(StatueEntity::class.java, TrackedDataHandlerRegistry.STRING)

        protected val DEFAULT_POSE: RawAnimation = RawAnimation.begin().thenPlayAndHold("animation.model.pose_1")
    }
}
