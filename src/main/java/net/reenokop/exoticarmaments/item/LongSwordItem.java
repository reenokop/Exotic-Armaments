package net.reenokop.exoticarmaments.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.MathHelper;
import net.reenokop.exoticarmaments.attribute.ModAttributes;

import java.util.List;

public class LongSwordItem extends Item {

    public LongSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(settings.maxDamage(material.durability()).repairable(material.repairItems())
                .enchantable(material.enchantmentValue()).component(DataComponentTypes.TOOL, createToolComponent())
                .attributeModifiers(createAttributeModifiers(material, attackDamage, attackSpeed)));
    }

    private static ToolComponent createToolComponent() {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);

        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(Blocks.COBWEB.getRegistryEntry()), 15.0F),
                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float attackDamage, float attackSpeed) {

        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                (attackDamage + material.attackDamageBonus()), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(
                        ModAttributes.SWEEPING_EDGE_RADIUS, new EntityAttributeModifier(
                                ModAttributes.SWEEPING_EDGE_RADIUS_MODIFIER_ID, 0.4, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(
                                ModAttributes.PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID, 0.8,
                                EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        target.takeKnockback(0.22F, MathHelper.sin(attacker.getYaw() * (float) (Math.PI / 180.0)),
                (-MathHelper.cos(attacker.getYaw() * (float) (Math.PI / 180.0))));
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

}
