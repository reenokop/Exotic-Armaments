package net.reenokop.exoticarmaments.mixin.equipment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.reenokop.exoticarmaments.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public class WitherSkeletonEntityMixin {

    @Inject(method = "initEquipment", at = @At(value = "HEAD"), cancellable = true)
    public void longSwordForWitherSkeleton(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {

        WitherSkeletonEntity witherSkeleton = (WitherSkeletonEntity) (Object) this;

        if (31 >= random.nextInt(100) + 1) {
            witherSkeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.STONE_LONG_SWORD));
            ci.cancel();
        }
    }

}
