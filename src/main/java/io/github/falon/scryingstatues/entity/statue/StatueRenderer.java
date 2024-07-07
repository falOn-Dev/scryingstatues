package io.github.falon.scryingstatues.entity.statue;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Axis;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StatueRenderer extends GeoEntityRenderer<StatueEntity> {
	public StatueRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new StatueModel());
	}

	@Override
	public void render(StatueEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
		poseStack.push();

		poseStack.multiply(Axis.Y_POSITIVE.rotationDegrees(360f - entityYaw));

		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

		poseStack.pop();
	}
}
