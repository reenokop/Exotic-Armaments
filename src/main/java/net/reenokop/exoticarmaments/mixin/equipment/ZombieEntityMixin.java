package net.reenokop.exoticarmaments.mixin.equipment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.reenokop.exoticarmaments.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    @Inject(method = "initEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I",
            shift = At.Shift.AFTER), cancellable = true)
    public void weaponsForZombieVariants(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {

        if (random.nextInt(3) == 0) {
            ZombieEntity zombie = (ZombieEntity) (Object) this;

            zombie.equipStack(EquipmentSlot.MAINHAND, new ItemStack(zombie instanceof HuskEntity
                    ? ModItems.IRON_MACHETE : ModItems.IRON_LONG_SWORD));
            ci.cancel();
        }
    }

}
