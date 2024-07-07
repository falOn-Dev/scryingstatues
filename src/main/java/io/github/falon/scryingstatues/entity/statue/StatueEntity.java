package io.github.falon.scryingstatues.entity.statue;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StatueEntity extends Entity implements GeoEntity {
	private static final TrackedData<String> NAME = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.STRING);

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	protected static final RawAnimation DEFAULT_POSE = RawAnimation.begin().thenPlayAndHold("pose_1");

	public StatueEntity(EntityType<?> variant, World world) {
		super(variant, world);
	}



	@Override
	protected void initDataTracker() {
		this.getDataTracker().startTracking(NAME, "");
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if(nbt.contains("name")) {
			this.getDataTracker().set(NAME, nbt.getString("name"));
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putString("name", this.getDataTracker().get(NAME));
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
