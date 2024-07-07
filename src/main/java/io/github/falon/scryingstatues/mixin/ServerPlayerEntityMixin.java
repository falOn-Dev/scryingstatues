package io.github.falon.scryingstatues.mixin;

import io.github.falon.scryingstatues.ScryingStatues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

	@Unique
	Vec3d returnPose = Vec3d.ZERO;
	@Unique
	double returnYaw = 0.0;
	@Unique
	double returnPitch = 0.0;

	@Shadow
	public abstract Entity getCameraEntity();

	@Shadow
	public abstract void teleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch);

	@Shadow
	public abstract ServerWorld getServerWorld();

	@Inject(
		method = "tick",
		at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/server/network/ServerPlayerEntity;setCameraEntity(Lnet/minecraft/entity/Entity;)V"),
		slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;shouldDismount()Z"))
	)
	private void doSomethingBeforeDismountSetCameraEntity(CallbackInfo ci) {
		ScryingStatues.LOGGER.info("Camera Entity: {}", getCameraEntity().getDisplayName().getString());
		ScryingStatues.LOGGER.info("Return Pose: {}", returnPose.toString());
		this.teleport(this.getServerWorld(), returnPose.x, returnPose.y, returnPose.z, (float) this.returnYaw, (float) this.returnPitch);
	}

	@Inject(method = "setCameraEntity", at = @At(value = "HEAD"))
	private void saveReturnPose(CallbackInfo ci) {
		this.returnPose = this.getCameraEntity().getPos();
		this.returnYaw = this.getCameraEntity().getYaw();
		this.returnPitch = this.getCameraEntity().getPitch();
	}
}
