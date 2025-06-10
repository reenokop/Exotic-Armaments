package net.reenokop.exoticarmaments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Hand;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.reenokop.exoticarmaments.ExoticArmamentsClient.clientPlayer;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends BipedEntityRenderState> {

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    public void saiParryRightArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {

        BipedEntityModel playerModel = (BipedEntityModel) (Object) this;

        if (playerModel instanceof PlayerEntityModel && state.isUsingItem && state.itemUseTime < 72000
                && clientPlayer.getMainHandStack().getItem() instanceof SaiItem && state.activeHand == Hand.MAIN_HAND) {
            float headYaw = playerModel.head.yaw;
            float headPitch = playerModel.head.pitch;

            playerModel.rightArm.yaw = -0.54F + headYaw;
            playerModel.rightArm.pitch = -1.3F + headYaw * 0.7F + (headPitch > 0.5F ? (headPitch - 0.5F) / 2 : 0);
            playerModel.rightArm.roll = 0.53F - headYaw * 0.4F;
            ci.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    public void saiParryLeftArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {

        BipedEntityModel playerModel = (BipedEntityModel) (Object) this;

        if (playerModel instanceof PlayerEntityModel && state.isUsingItem && state.itemUseTime > 0
                && clientPlayer.getOffHandStack().getItem() instanceof SaiItem && state.activeHand == Hand.OFF_HAND) {

            float headYaw = playerModel.head.yaw;
            float headPitch = playerModel.head.pitch;

            playerModel.leftArm.yaw = 0.33F + headYaw / 1.33F + (headPitch > 0 ? headPitch * 0.4F : 0);
            playerModel.leftArm.pitch = -1.66F - headYaw * 0.15F;
            playerModel.leftArm.roll = -0.39F + headYaw * 0.66F - (headPitch > 0 ? headPitch * 0.55F : 0);
            ci.cancel();
        }
    }

}
