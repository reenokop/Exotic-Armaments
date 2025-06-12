package net.reenokop.exoticarmaments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.reenokop.exoticarmaments.item.MacheteItem;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	// Machete
	@Inject(method = "getWeaponDisableBlockingForSeconds", at = @At("HEAD"), cancellable = true)
	public void macheteDisablesShield(CallbackInfoReturnable<Float> cir) {

		LivingEntity livingEntity = (LivingEntity) (Object) this;

		if (!(livingEntity instanceof PlayerEntity)
				&& livingEntity.getMainHandStack().getItem() instanceof MacheteItem weapon
				&& weapon.disableChance >= Random.create().nextInt(100) + 1) {
			cir.setReturnValue(5.0F);
		}
	}

	//Sai
	@Inject(method = "damage", at = @At(value = "HEAD"))
	public void saiBypassCooldown(ServerWorld world, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {

		LivingEntity livingEntity = (LivingEntity) (Object) this;

		if (damageSource.getAttacker() instanceof PlayerEntity player && player.getMainHandStack().getItem() instanceof SaiItem) {
			livingEntity.timeUntilRegen -= 6;
		}
	}

}