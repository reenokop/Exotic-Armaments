package net.reenokop.exoticarmaments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

	@Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target =
			"Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z", shift = At.Shift.AFTER, ordinal = 1))
	public void saiParry(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress,
			ItemStack stack, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

		if (stack.getItem() instanceof SaiItem && player.getActiveItem().getItem() instanceof SaiItem
				&& player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {

			Arm arm = hand == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
			float x = (float)Math.pow((player.getItemUseTimeLeft() - tickDelta + 1) / stack.getMaxUseTime(player), 120000);
			float y = 1 - x;

			if (arm == Arm.RIGHT) {
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(26.0F));
				matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(77.0F));
				matrices.translate(y * 0.1, y * 0.13, y * 0.57);

			} else {
				matrices.translate(0, -30 * x, 0);
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-170.0F));
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-100.0F));
				matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(14.0F));
				matrices.translate(1.35, y * 0.51, 0.87);
			}
		}
	}

}
