package net.reenokop.exoticarmaments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    public void saiParryRightArm(LivingEntity livingEntity, CallbackInfo ci) {

        if (livingEntity instanceof PlayerEntity player && player.isUsingItem()&& player.getItemUseTimeLeft() > 0
                && player.getActiveItem().getItem() instanceof SaiItem && player.getActiveHand() == Hand.MAIN_HAND) {

            BipedEntityModel playerModel = (BipedEntityModel) (Object) this;
            float headYaw = playerModel.head.yaw;
            float headPitch = playerModel.head.pitch;

            playerModel.rightArm.yaw = -0.54F + headYaw;
            playerModel.rightArm.pitch = -1.3F + headYaw * 0.7F + (headPitch > 0.5F ? (headPitch - 0.5F) / 2 : 0);
            playerModel.rightArm.roll = 0.53F - headYaw * 0.4F;
            ci.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    public void saiParryLeftArm(LivingEntity livingEntity, CallbackInfo ci) {

        if (livingEntity instanceof PlayerEntity player && player.isUsingItem() && player.getItemUseTimeLeft() > 0
                && player.getActiveItem().getItem() instanceof SaiItem && player.getActiveHand() == Hand.OFF_HAND) {

            BipedEntityModel playerModel = (BipedEntityModel) (Object) this;
            float headYaw = playerModel.head.yaw;
            float headPitch = playerModel.head.pitch;

            playerModel.leftArm.yaw = 0.33F + headYaw / 1.33F + (headPitch > 0 ? headPitch * 0.4F : 0);
            playerModel.leftArm.pitch = -1.66F - headYaw * 0.15F;
            playerModel.leftArm.roll = -0.39F + headYaw * 0.66F - (headPitch > 0 ? headPitch * 0.55F : 0);
            ci.cancel();
        }
    }

}
