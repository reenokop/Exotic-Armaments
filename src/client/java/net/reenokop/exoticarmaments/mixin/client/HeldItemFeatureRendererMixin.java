package net.reenokop.exoticarmaments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
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
public class HeldItemFeatureRendererMixin {

    @Inject(method="renderItem", at=@At(value="INVOKE", target= "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(" +
            "Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/" +
            "ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            shift=At.Shift.BEFORE))
    public void saiParry(LivingEntity livingEntity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm,
                         MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {

        if (livingEntity instanceof PlayerEntity player && player.getActiveItem().getItem() instanceof SaiItem && player.isUsingItem()
                && player.getItemUseTimeLeft() > 0) {

            boolean isRightArm = arm == Arm.RIGHT;
            boolean isLeftArm = arm == Arm.LEFT;

            if (player.getActiveHand() == Hand.MAIN_HAND) {

                if (isRightArm) {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(12.0F));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(59.0F));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(5.5F));
                    matrices.translate(-0.086, 0.0, -0.03);
                }

                if (isLeftArm && !(player.getOffHandStack().getItem() instanceof ShieldItem)
                        && !(player.getOffHandStack().getItem() instanceof SpyglassItem)
                        && !(player.getOffHandStack().getItem() instanceof CrossbowItem)
                        && !(player.getOffHandStack().getItem() instanceof BowItem)
                        && !(player.getOffHandStack().getItem() instanceof TridentItem)
                        && !(player.getOffHandStack().getItem() instanceof BlockItem)){
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

                if (isRightArm && !(player.getMainHandStack().getItem() instanceof BlockItem)) {
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(140.0F));
                    matrices.translate(0.0, 0.245, 0.03);
                }
            }
        }
    }

}
