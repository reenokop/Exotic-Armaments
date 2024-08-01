package net.reenokop.exoticarmaments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.reenokop.exoticarmaments.item.LongSwordItem;
import net.reenokop.exoticarmaments.item.SaiItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.*;

import static net.reenokop.exoticarmaments.item.SaiItem.sais;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {

    // Long Sword
    @Inject(method = "attack", at = @At("HEAD"))
    public void longSwordSweeping(Entity target, CallbackInfo ci) {

        if (target.isAttackable()) {

            PlayerEntity player = (PlayerEntity) (Object) this;
            DamageSource damageSource = player.getDamageSources().playerAttack(player);
            float cooldown = player.getAttackCooldownProgress(0.5F);

            if (player.getMainHandStack().getItem() instanceof LongSwordItem) {

                for (LivingEntity livingEntity3 : player.getWorld().getNonSpectatingEntities(LivingEntity.class,
                        target.getBoundingBox().expand(1.4, 0.65, 1.4))) {

                    double distanceSqrt = player.squaredDistanceTo(livingEntity3);
                    float sweepingDamage = (float) (1.0F + player.getAttributeValue(EntityAttributes.PLAYER_SWEEPING_DAMAGE_RATIO)
                            * (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                            + (1.5 - 1.5 * Math.sqrt(distanceSqrt) / 3.6));

                    if (crit(target)) {
                        sweepingDamage *= 1.4F;
                    }

                    if (livingEntity3 != player
                            && livingEntity3 != target
                            && !player.isTeammate(livingEntity3)
                            && (!(livingEntity3 instanceof ArmorStandEntity) || !((ArmorStandEntity) livingEntity3).isMarker())
                            && distanceSqrt < 18.0) {
                        float attackDamage = getDamageAgainst(livingEntity3, sweepingDamage, damageSource) * cooldown;
                        livingEntity3.takeKnockback(0.5F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)),
                               (-MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0))));
                        livingEntity3.damage(damageSource, attackDamage);
                        if (player.getWorld() instanceof ServerWorld serverWorld) {
                            EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity3, damageSource);
                        }
                    }
                }

                if (cooldown > 0.5F) {
                    player.spawnSweepAttackParticles();
                }

                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
            }
        }
    }

    @Unique
    public boolean crit(Entity target) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        return player.getAttackCooldownProgress(0.5F) > 0.9F
                && player.fallDistance > 0.0F
                && !player.isOnGround()
                && !player.isClimbing()
                && !player.isTouchingWater()
                && !player.hasStatusEffect(StatusEffects.BLINDNESS)
                && !player.hasVehicle()
                && target instanceof LivingEntity
                && !player.isSprinting();
    }

    @Shadow
    protected float getDamageAgainst(Entity target, float baseDamage, DamageSource damageSource) {
        return baseDamage;
    }

    // Sai - dual wielding
    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("HEAD"), cancellable = true)
    public void saiAttackCooldownFromDualWield(CallbackInfoReturnable<Float> cir) {

        if (dualWield()) {

            PlayerEntity player = (PlayerEntity) (Object) this;
            float attackSpeed = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
            SaiItem weapon = (SaiItem) player.getMainHandStack().getItem();
            SaiItem subWeapon = (SaiItem) player.getEquippedStack(EquipmentSlot.OFFHAND).getItem();
            boolean leftHandRule = SaiItem.leftHandRule.getOrDefault(player.getUuid(), false);


            if (!leftHandRule) { // Apply the mainhand weapon's cooldown after attacking with the offhand and vice versa.
                cir.setReturnValue((1.0F / (attackSpeed - weapon.getSaiSpeed(weapon.getMaterial()) + weapon.dualWieldAttackSpeed) * 20.0F));

            } else {
                cir.setReturnValue((1.0F / (attackSpeed - weapon.getSaiSpeed(weapon.getMaterial()) + subWeapon.dualWieldAttackSpeed) * 20.0F));
            }
        }
    }

    @ModifyVariable(method = "attack", at = @At(value = "LOAD", ordinal = 0), ordinal = 0)
    public float saiDamageAlternation(float baseDamage, Entity target) {

        PlayerEntity player = (PlayerEntity) (Object) this;
        boolean leftHandRule = SaiItem.leftHandRule.getOrDefault(player.getUuid(), false);

        if (dualWield() && leftHandRule) {
            SaiItem weapon = (SaiItem) player.getMainHandStack().getItem();
            SaiItem subWeapon = (SaiItem) player.getOffHandStack().getItem();

            return (float)player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                    - weapon.getSaiDamage(weapon.getMaterial()) + subWeapon.offHandAttackDamage;
        }

        return baseDamage;
    }

    @Unique
    public boolean dualWield() {

        PlayerEntity player = (PlayerEntity) (Object) this;

        return player.getMainHandStack().getItem() instanceof SaiItem && player.getOffHandStack().getItem() instanceof SaiItem;
    }

    // Sai - parrying
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void saiAttackPrevention(Entity target, CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity)(Object)this;
        Item weapon = player.getMainHandStack().getItem();
        Set<Item> cooldownWeapons = SaiItem.cooldownFromSai.get(player);

        if (player.getItemCooldownManager().isCoolingDown(weapon) && cooldownWeapons != null && cooldownWeapons.contains(weapon)) {
            ci.cancel();

        } else {
            removeWeaponFromCooldown(player, weapon);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
    public float saiParry(float amount, DamageSource damageSource) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getActiveItem().getItem() instanceof SaiItem weapon && damageSource.getAttacker() instanceof LivingEntity attacker
                && weapon.getMaxUseTime(player.getActiveItem(), player) - player.getItemUseTimeLeft() >= 3 && saiParryAngle(damageSource)
                && !(attacker instanceof PufferfishEntity) && !(attacker instanceof GuardianEntity) && damageSource.isDirect()
                && (damageSource.isOf(DamageTypes.MOB_ATTACK) || damageSource.isOf(DamageTypes.MOB_ATTACK_NO_AGGRO)
                || damageSource.isOf(DamageTypes.PLAYER_ATTACK) || damageSource.isOf(DamageTypes.STING))) {

            // Unwanted animation
            //player.getActiveItem().damage((int) amount / 5 + 1, player, LivingEntity.getSlotForHand(player.getActiveHand()));

            // Occasionally disable sais if the attack is too strong to parry
            if (amount >= 25 && 4 * ((amount >= 50 ? 50 : amount) - 25) > Random.create().nextInt(101)) {
                amount /= 1.2F;

                for (Item sai : sais) {
                    player.getItemCooldownManager().set(sai, 110);
                    addItemToCooldown(player, sai);
                }
                player.clearActiveItem();
                attacker.getWorld().playSound(player, (attacker.getX() + player.getX()) / 2, (attacker.getY()
                                + player.getY()) / 2, (attacker.getZ() + player.getZ()) / 2, SoundEvents.BLOCK_ANVIL_LAND,
                        SoundCategory.PLAYERS, 0.3F, 0.8F + attacker.getWorld().random.nextFloat() * 0.4F);
                attacker.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 0.4F + player.getWorld().random.nextFloat() * 0.4F);

                // Parry successfully
            } else {
                amount /= 2;

                // Occasionally hinder the opponent
                if (weapon.disarmChance >= Random.create().nextInt(100) + 1) {

                    if (attacker instanceof PlayerEntity attackingPlayer && !attackingPlayer.isCreative()) {
                        attackingPlayer.getItemCooldownManager().set(Items.SHIELD, (int) (weapon.disarmDuration / 1.5));

                        ItemStack offHandStack = attackingPlayer.getOffHandStack();

                        disableItem(attackingPlayer, offHandStack, offHandStack.getItem(), weapon.disarmDuration);

                        for (int i = 0; i < 9; i++) {
                            ItemStack stack = attackingPlayer.getInventory().getStack(i);

                            disableItem(attackingPlayer, stack, stack.getItem(), weapon.disarmDuration);
                        }

                    } else if (attacker instanceof MobEntity) {
                        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, weapon.disarmDuration, 1));
                        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weapon.disarmDuration, 1));
                    }

                    attacker.clearActiveItem();
                    attacker.getMainHandStack().damage((int) weapon.getSaiDamage(weapon.getMaterial()) - 1, attacker, EquipmentSlot.MAINHAND);

                    float parryDamage = weapon.getSaiDamage(weapon.getMaterial()) / 1.5F;

                    if (attacker.getMainHandStack().isEmpty() && attacker.getHealth() > parryDamage) {
                        attacker.damage(player.getDamageSources().playerAttack(player), parryDamage);
                        attacker.takeKnockback(0.11F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)),
                            (-MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0))));

                    } else {
                        attacker.takeKnockback(0.3F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)),
                            (-MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0)))); // Only works against mobs
                    }

                    attacker.getWorld().playSound(player, (attacker.getX() + player.getX()) / 2, (attacker.getY()
                                    + player.getY()) / 2, (attacker.getZ() + player.getZ()) / 2, SoundEvents.ITEM_SHIELD_BREAK,
                            SoundCategory.PLAYERS, 1.1F, 0.8F + attacker.getWorld().random.nextFloat() * 0.4F);
                    attacker.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.2F, 0.4F + player.getWorld().random.nextFloat() * 0.4F);

                } else {
                    attacker.getWorld().playSound(player, (attacker.getX() + player.getX()) / 2, (attacker.getY()
                                    + player.getY()) / 2, (attacker.getZ() + player.getZ()) / 2, SoundEvents.ITEM_MACE_SMASH_AIR,
                            SoundCategory.PLAYERS, 1.2F, 1.6F + attacker.getWorld().random.nextFloat() * 0.4F);
                    attacker.playSound(SoundEvents.ITEM_MACE_SMASH_AIR, 1.6F, 1.6F + player.getWorld().random.nextFloat() * 0.4F);
                    attacker.takeKnockback(0.3F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)),
                            (-MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0)))); // Only works against mobs
                }
            }
        }

        return amount;
    }

    @Unique
    public boolean saiParryAngle(DamageSource damageSource) {

        PlayerEntity player = (PlayerEntity) (Object) this;
        Vec3d vec3d = damageSource.getPosition();

        if (vec3d != null) {
            Vec3d vec3d2 = player.getRotationVector(0.0F, player.getHeadYaw());
            Vec3d vec3d3 = vec3d.relativize(player.getPos());
            vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z).normalize();

            return vec3d3.dotProduct(vec3d2) < 0.0;
        }

        return false;
    }

    @Unique
    public void disableItem(PlayerEntity player, ItemStack stack, Item weapon, int disarmDuration) {

        if (!stack.isEmpty() && !(player.getItemCooldownManager().isCoolingDown(weapon))) {
            player.getItemCooldownManager().set(weapon, disarmDuration);
            addItemToCooldown(player, weapon);
        }
    }

    @Unique
    public void addItemToCooldown(PlayerEntity player, Item weapon) { // The player can't attack whilst holding these items

        Set<Item> weapons = SaiItem.cooldownFromSai.getOrDefault(player, new HashSet<>());
        weapons.add(weapon);
        SaiItem.cooldownFromSai.put(player, weapons);
    }

    @Unique
    public void removeWeaponFromCooldown(PlayerEntity player, Item item) {

        Set<Item> weapons = SaiItem.cooldownFromSai.get(player);

        if (weapons != null) {
            weapons.remove(item);

            if (weapons.isEmpty()) {
                SaiItem.cooldownFromSai.remove(player);

            } else {
                SaiItem.cooldownFromSai.put(player, weapons);
            }
        }
    }

    // Sai - animation
    @Inject(method = "getMaxRelativeHeadRotation", at = @At("HEAD"), cancellable = true)
    public void hideBehindSai(CallbackInfoReturnable<Float> cir) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getActiveItem().getItem() instanceof SaiItem) {
            cir.setReturnValue(18.5F);
        }
    }

}

