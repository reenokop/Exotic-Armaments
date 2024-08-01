package net.reenokop.exoticarmaments.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.reenokop.exoticarmaments.attribute.ModAttributes;

import java.util.List;

public class LongSwordItem extends ToolItem {

    public LongSwordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0F),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float attackDamage, float attackSpeed) {

        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                (attackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(
                        ModAttributes.SWEEPING_EDGE_RADIUS, new EntityAttributeModifier(
                                ModAttributes.SWEEPING_EDGE_RADIUS_MODIFIER_ID, 0.4, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(
                                ModAttributes.PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID, 0.8,
                                EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        target.takeKnockback(0.22F, MathHelper.sin(attacker.getYaw() * (float) (Math.PI / 180.0)),
                (-MathHelper.cos(attacker.getYaw() * (float) (Math.PI / 180.0))));
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);

        return true;
    }

}
