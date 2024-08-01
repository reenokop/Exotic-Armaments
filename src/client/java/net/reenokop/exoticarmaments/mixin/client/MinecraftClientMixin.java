package net.reenokop.exoticarmaments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.reenokop.exoticarmaments.item.SaiItem;
import net.reenokop.exoticarmaments.network.DualWieldPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;
    @Shadow public abstract boolean isInSingleplayer();

    @Redirect(method = "doAttack", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V", shift = At.Shift.BEFORE))
    public Hand doDualWield() {

        if (player != null && player.getMainHandStack().getItem() instanceof SaiItem
                && player.getOffHandStack().getItem() instanceof SaiItem) {
            UUID uuid = player.getUuid();
            boolean leftHandRule = SaiItem.leftHandRule.getOrDefault(uuid, false);

            ClientPlayNetworking.send(new DualWieldPacket(uuid, !leftHandRule));

            if (!isInSingleplayer()) {
                SaiItem.leftHandRule.put(uuid, !leftHandRule);
            }

            if (leftHandRule) {
                return Hand.OFF_HAND;
            }
        }

        return Hand.MAIN_HAND;
    }

}
