package net.reenokop.exoticarmaments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;
import net.reenokop.exoticarmaments.item.MacheteItem;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	// Machete
	@Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
	public void macheteDisablesShield(CallbackInfoReturnable<Boolean> cir) {

		LivingEntity livingEntity = (LivingEntity) (Object) this;

		if (livingEntity.getMainHandStack().getItem() instanceof MacheteItem weapon && weapon.disableChance + (crit(livingEntity)
				? Math.ceil(Math.sqrt((double) weapon.disableChance / 10)) : 0) >= Random.create().nextInt(100) + 1) {
			cir.setReturnValue(true);
		}
	}

	@Unique
	public boolean crit(LivingEntity livingEntity) {

		return livingEntity instanceof PlayerEntity player
				// && player.getAttackCooldownProgress(0.5F) > 0.9F bugged
				&& player.fallDistance > 0.0F
				&& !player.isOnGround()
				&& !player.isClimbing()
				&& !player.isTouchingWater()
				&& !player.hasStatusEffect(StatusEffects.BLINDNESS)
				&& !player.hasVehicle()
				&& !player.isSprinting();
	}

	//Sai
	@Inject(method = "damage", at = @At(value = "HEAD"))
	public void saiBypassCooldown(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {

		LivingEntity livingEntity = (LivingEntity) (Object) this;

		if (damageSource.getAttacker() instanceof PlayerEntity player && player.getMainHandStack().getItem() instanceof SaiItem) {
			livingEntity.timeUntilRegen -= 6;
		}
	}

}