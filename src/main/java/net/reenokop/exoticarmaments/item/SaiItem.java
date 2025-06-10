package net.reenokop.exoticarmaments.item;

import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.reenokop.exoticarmaments.attribute.ModAttributes;

import java.util.*;

public class SaiItem extends Item {

    public SaiItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings,
                   float offHandAttackDamage, float dualWieldAttackSpeed, int disarmChance, int disarmDuration) {

        super(settings.maxDamage(material.durability()).repairable(material.repairItems())
                .enchantable(material.enchantmentValue()).attributeModifiers(createAttributeModifiers(material,
                        attackDamage, attackSpeed, offHandAttackDamage, dualWieldAttackSpeed)));

        this.offHandAttackDamage = offHandAttackDamage;
        this.dualWieldAttackSpeed = dualWieldAttackSpeed;
        this.disarmChance = disarmChance;
        this.disarmDuration = disarmDuration;
        this.material = material;
    }

    public static Map<UUID, Boolean> leftHandRule = new HashMap<>(); // Monsoon reference
    public float offHandAttackDamage;
    public float dualWieldAttackSpeed;
    public int disarmChance;
    public int disarmDuration;
    public static List<Item> sais = new ArrayList<>();
    public static Map<PlayerEntity, Set<Item>> cooldownFromSai = new HashMap<>();
    private final ToolMaterial material;

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float attackDamage, float attackSpeed,
            float offHandAttackDamage, float dualWieldAttackSpeed) {

        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                (attackDamage + material.attackDamageBonus()), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(
                        ModAttributes.OFFHAND_ATTACK_DAMAGE, new EntityAttributeModifier(ModAttributes.OFFHAND_ATTACK_DAMAGE_MODIFIER_ID,
                                offHandAttackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.OFFHAND)
                .add(
                        ModAttributes.DUAL_WIELD_ATTACK_SPEED, new EntityAttributeModifier(ModAttributes.DUAL_WIELD_ATTACK_SPEED_MODIFIER_ID,
                                dualWieldAttackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.OFFHAND)
                .build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        target.takeKnockback(0.11F, MathHelper.sin(attacker.getYaw() * (float) (Math.PI / 180.0)),
                (-MathHelper.cos(attacker.getYaw() * (float) (Math.PI / 180.0))));

        if (attacker instanceof PlayerEntity player) {
            ItemStack offHandStack = player.getOffHandStack();
            boolean leftHandRule = SaiItem.leftHandRule.getOrDefault(player.getUuid(), false);

            if (offHandStack.getItem() instanceof SaiItem && leftHandRule) {
                offHandStack.damage(1, attacker, EquipmentSlot.OFFHAND);
                return true;
            }
        }

        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {

        if (state.getHardness(world, pos) != 0) {
            stack.damage(2, miner, EquipmentSlot.MAINHAND);
            return true;
        }

        return false;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

//        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    public float getSaiDamage(ToolMaterial material) {
        if (material == ToolMaterial.WOOD || material == ToolMaterial.GOLD) {
            return 2.0F;
        } else if (material == ToolMaterial.DIAMOND) {
            return 3.0F;
        } else if (material == ToolMaterial.NETHERITE) {
            return 3.5F;
        } else {
            return 2.5F;
        }
    }

    public float getSaiSpeed(ToolMaterial toolMaterial) {
        if (toolMaterial == ToolMaterial.WOOD || toolMaterial == ToolMaterial.GOLD
                || toolMaterial == ToolMaterial.STONE) {
            return 1.9F;
        } else {
            return 2.2F;
        }
    }

    public ToolMaterial getMaterial() { return material; }

}
