package net.reenokop.exoticarmaments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemFeatureRenderer.class)
public class HeldItemFeatureRendererMixin<S extends LivingEntityRenderState> {

    @Inject(method = "renderItem", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", shift = At.Shift.AFTER))
    private void saiParry(S state, BakedModel model, ItemStack stack, ModelTransformationMode transformationMode,
            Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

        if (state instanceof PlayerEntityRenderState playerEntityRenderState) {
            Item playerItem = playerEntityRenderState.activeHand == Hand.MAIN_HAND ?
                    playerEntityRenderState.getMainHandStack().getItem() : playerEntityRenderState.leftHandStack.getItem();

            if (playerItem instanceof SaiItem && playerEntityRenderState.isUsingItem && playerEntityRenderState.itemUseTimeLeft > 0) {

                boolean isRightArm = arm == Arm.RIGHT;
                boolean isLeftArm = arm == Arm.LEFT;

                if (playerEntityRenderState.activeHand == Hand.MAIN_HAND) {

                    if (isRightArm) {
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(12.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(59.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(5.5F));
                        matrices.translate(-0.086, 0.0, -0.03);
                    }

                    if (isLeftArm && !(playerEntityRenderState.leftHandStack.getItem() instanceof ShieldItem)
                            && !(playerEntityRenderState.leftHandStack.getItem() instanceof SpyglassItem)
                            && !(playerEntityRenderState.leftHandStack.getItem() instanceof CrossbowItem)
                            && !(playerEntityRenderState.leftHandStack.getItem() instanceof BowItem)
                            && !(playerEntityRenderState.leftHandStack.getItem() instanceof TridentItem)
                            && !(playerEntityRenderState.leftHandStack.getItem() instanceof BlockItem)) {
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(195.0F));
                        matrices.translate(0.0, 0.23, -0.18);
                    }

                } else {
                    if (isLeftArm) {
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(12.5F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(115.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(170.0F));
                        matrices.translate(-0.0128, -0.0728, -0.0622);
                    }

                    if (isRightArm && !(playerEntityRenderState.getMainHandStack().getItem() instanceof BlockItem)) {
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(140.0F));
                        matrices.translate(0.0, 0.245, 0.03);
                    }
                }
            }
        }
    }

}
