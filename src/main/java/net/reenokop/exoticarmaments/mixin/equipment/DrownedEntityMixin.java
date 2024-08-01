package net.reenokop.exoticarmaments.mixin.equipment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.reenokop.exoticarmaments.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedEntity.class)
public class DrownedEntityMixin {

    @Inject(method = "initEquipment", at = @At(value = "HEAD"), cancellable = true)
    public void saisForDrowned(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {

        if (random.nextInt(13) == 0) {
            DrownedEntity drowned = (DrownedEntity) (Object) this;

            drowned.equipStack(EquipmentSlot.MAINHAND, new ItemStack(pickSai(random)));
            drowned.equipStack(EquipmentSlot.OFFHAND, new ItemStack(pickSai(random)));
            ci.cancel();
        }
    }

    @Unique
    public Item pickSai(Random random) {
        return random.nextInt(2) == 0 ? ModItems.IRON_SAI : ModItems.STONE_SAI;
    }

}
