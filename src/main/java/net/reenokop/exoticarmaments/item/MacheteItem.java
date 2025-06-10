package net.reenokop.exoticarmaments.item;

import net.minecraft.block.Block;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.reenokop.exoticarmaments.attribute.ModAttributes;
import net.reenokop.exoticarmaments.tag.ModTagKeys;

import java.util.List;

public class MacheteItem extends Item {

    public int disableChance;

    public MacheteItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings,
                       float axeMinineableSpeed, float hoeMineableSpeed, int disableChance) {
        super(settings.maxDamage(material.durability()).repairable(material.repairItems())
                .enchantable(material.enchantmentValue()).component(DataComponentTypes.TOOL,
                        createToolComponent(axeMinineableSpeed, hoeMineableSpeed))
                .attributeModifiers(createAttributeModifiers(material, attackDamage, attackSpeed, disableChance)));
        this.disableChance = disableChance;
    }

    private static ToolComponent createToolComponent(float axeMineableSpeed, float hoeMineableSpeed) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);

        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(Blocks.COBWEB.getRegistryEntry()), 16.0F),
                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.AXE_MINEABLE), axeMineableSpeed),
                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.HOE_MINEABLE), hoeMineableSpeed),
                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.4F)), 1.0F, 2);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float attackDamage, float attackSpeed,
            int disableChance) {

        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                (attackDamage + material.attackDamageBonus()), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(
                        ModAttributes.SHIELD_DISABLE_CHANCE, new EntityAttributeModifier(
                                ModAttributes.SHIELD_DISABLE_CHANCE_MODIFIER_ID, disableChance,
                                EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative() || state.isIn(ModTagKeys.MACHETE_EFFICIENT);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {

        ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);

        if (toolComponent != null && !world.isClient && state.getHardness(world, pos) != 0.0F && toolComponent.damagePerBlock() > 0) {
            stack.damage(state.isIn(ModTagKeys.MACHETE_EFFICIENT) ? 1: 2, miner, EquipmentSlot.MAINHAND);
            return true;
        }

        return false;
    }

}
