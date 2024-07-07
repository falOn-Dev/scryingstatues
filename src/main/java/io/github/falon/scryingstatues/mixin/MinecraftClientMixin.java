package io.github.falon.scryingstatues.mixin;

import io.github.falon.scryingstatues.entity.statue.StatueEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	@Final
	public GameOptions options;

	@Inject(method = "setCameraEntity", at = @At("HEAD"))
	private void setCameraEntity(Entity entity, CallbackInfo ci) {
		this.options.hudHidden = entity instanceof StatueEntity;
	}
}
