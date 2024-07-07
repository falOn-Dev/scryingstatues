package io.github.falon.scryingstatues.entity.statue;

import io.github.falon.scryingstatues.ScryingStatues;
import io.github.falon.scryingstatues.item.ScryingMirrorItem;
import io.github.falon.scryingstatues.item.ShattererItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

public class StatueEntity extends Entity implements GeoEntity {
	private static final TrackedData<Integer> HEALTH = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	protected static final RawAnimation DEFAULT_POSE = RawAnimation.begin().thenPlayAndHold("animation.model.pose_1");

	public StatueEntity(EntityType<?> variant, World world) {
		super(variant, world);
		this.inanimate = true;
	}

	@Override
	public boolean collides() {
		return true;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() instanceof ShattererItem) {
			player.playSound(SoundEvents.BLOCK_STONE_PLACE, 1.0F, 1.0F);

			ScryingStatues.LOGGER.info("Shattering entity: {}", this.toString());

			this.getDataTracker().set(HEALTH, this.getDataTracker().get(HEALTH) - 1);
			this.getWorld().addBlockBreakParticles(BlockPos.create(this.getX(), this.getY(), this.getZ()), Blocks.STONE.getDefaultState());

			stack.damage(1, player, (playerEntity) -> {
				playerEntity.sendToolBreakStatus(hand);
			});

			if (this.getDataTracker().get(HEALTH) <= 0) {
				this.remove(RemovalReason.KILLED);
				this.dropStack(new ItemStack(Items.STONE, 3));
			}

			return ActionResult.SUCCESS;
		} else if (stack.getItem() instanceof ScryingMirrorItem){
			if (this.getOwner().isPresent() &&  player.isSneaking()) {
				stack.getOrCreateNbt().putUuid("target", this.getUuid());
				player.sendMessage(Text.translatable("item.scryingstatues.scrying_mirror.set_message"), true);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	@Override
	protected void initDataTracker() {
		this.getDataTracker().startTracking(HEALTH, 3);
		this.getDataTracker().startTracking(OWNER, Optional.empty());
	}

	public Optional<UUID> getOwner() {
		return this.getDataTracker().get(OWNER);
	}

	public void setOwner(UUID owner) {
		this.getDataTracker().set(OWNER, Optional.of(owner));
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if (nbt.contains("health")) {
			this.getDataTracker().set(HEALTH, nbt.getInt("health"));
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("health", this.getDataTracker().get(HEALTH));
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 20, this::predicate));
	}

	private PlayState predicate(AnimationState<StatueEntity> statueEntityAnimationState) {
		return statueEntityAnimationState.setAndContinue(DEFAULT_POSE);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}
}
