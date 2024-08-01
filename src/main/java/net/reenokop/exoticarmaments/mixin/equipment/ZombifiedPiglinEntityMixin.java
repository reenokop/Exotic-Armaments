package net.reenokop.exoticarmaments.mixin.equipment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.reenokop.exoticarmaments.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombifiedPiglinEntity.class)
public class ZombifiedPiglinEntityMixin {

    @Inject(method = "initEquipment", at = @At(value = "HEAD"), cancellable = true)
    public void weaponsForZombifiedPiglin(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {

        ZombifiedPiglinEntity zombifiedPiglin = (ZombifiedPiglinEntity) (Object) this;
        int chance = random.nextInt(100) + 1;

        if (5 >= chance) {
            zombifiedPiglin.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.GOLDEN_SAI));

            if (random.nextInt(3) != 2) {
                zombifiedPiglin.equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModItems.GOLDEN_SAI));
            }

            ci.cancel();

        } else if (14 >= chance) {
            zombifiedPiglin.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.GOLDEN_MACHETE));

            if (random.nextInt(5) == 0) {
                zombifiedPiglin.equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModItems.GOLDEN_SAI));
            }

            ci.cancel();
        }
    }

}
