package net.reenokop.exoticarmaments.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.reenokop.exoticarmaments.ExoticArmamentsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("HEAD"), cancellable = true)
    public void getPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, PlayerEntityRenderState playerEntityRenderState, float f, CallbackInfo ci) {
        ExoticArmamentsClient.clientPlayer = abstractClientPlayerEntity;
    }
}
