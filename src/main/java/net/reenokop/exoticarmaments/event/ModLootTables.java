package net.reenokop.exoticarmaments.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.RaiderPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;
import net.reenokop.exoticarmaments.item.ModItems;

public class ModLootTables {

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, listener) -> {

            // Mobs
            if (source.isBuiltin() && EntityType.PIGLIN_BRUTE.getLootTableId().equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.17F))
                        .rolls(UniformLootNumberProvider.create(1, 2))
                        .with(ItemEntry.builder(ModItems.GOLDEN_SAI)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 0.99F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(5, 25))
                                        .conditionally(RandomChanceLootCondition.builder(0.63F))));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && EntityType.PILLAGER.getLootTableId().equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(21)
                                .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.create().typeSpecific(RaiderPredicate.CAPTAIN_WITHOUT_RAID)))
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.77F, 0.99F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(1, 15))
                                        .conditionally(RandomChanceLootCondition.builder(0.2F))))
                        .with(ItemEntry.builder(ModItems.WOODEN_MACHETE).weight(3)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.7F, 0.9F))))
                        .with(EmptyEntry.builder().weight(19));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && EntityType.ILLUSIONER.getLootTableId().equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.23F))
                        .rolls(UniformLootNumberProvider.create(1, 2))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(7)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.7F, 0.9F))))
                        .with(ItemEntry.builder(ModItems.STONE_SAI).weight(5)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.6F, 0.85F))));

                tableBuilder.pool(poolBuilder);
            }

            // "Mojang is scared of mob drops"      don't worry I got you
            if (source.isBuiltin() && (EntityType.FROG.getLootTableId().equals(key)
                    || EntityType.GOAT.getLootTableId().equals(key)
                    || EntityType.ARMADILLO.getLootTableId().equals(key))) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WOODEN_MACHETE).weight(2)
                                .conditionally(KilledByPlayerLootCondition.builder())
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.01F, 0.35F))))
                        .with(EmptyEntry.builder().weight(5));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && (EntityType.TADPOLE.getLootTableId().equals(key)
                    || EntityType.AXOLOTL.getLootTableId().equals(key)
                    || EntityType.ALLAY.getLootTableId().equals(key))) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WOODEN_LONG_SWORD).weight(2)
                                .conditionally(KilledByPlayerLootCondition.builder())
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.01F, 0.35F))))
                        .with(EmptyEntry.builder().weight(5));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && (EntityType.SNIFFER.getLootTableId().equals(key)
                    || EntityType.CAMEL.getLootTableId().equals(key))) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WOODEN_SAI).weight(2)
                                .conditionally(KilledByPlayerLootCondition.builder())
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.01F, 0.35F))))
                        .with(EmptyEntry.builder().weight(5));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && EntityType.WARDEN.getLootTableId().equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WOODEN_SAI)
                                .conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                        .tag(TagPredicate.expected(DamageTypeTags.IS_DROWNING))))
                                .apply(SetDamageLootFunction.builder(ConstantLootNumberProvider.create(0.01F)))
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener
                                        .getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.LUCK_OF_THE_SEA),
                                        ConstantLootNumberProvider.create(4))))
                        .with(EmptyEntry.builder());

                tableBuilder.pool(poolBuilder);
            }

            // Trial chambers
            if (source.isBuiltin() && LootTables.TRIAL_CHAMBERS_ENTRANCE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.WOODEN_LONG_SWORD))
                        .with(EmptyEntry.builder().weight(2));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.TRIAL_CHAMBERS_INTERSECTION_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DIAMOND_MACHETE)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.2F, 0.6F))))
                        .with(EmptyEntry.builder().weight(6));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.GOLDEN_LONG_SWORD).weight(4)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.2F, 0.8F))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_SAI)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.4F, 0.9F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(10, 25))
                                        .conditionally(RandomChanceLootCondition.builder(0.5F))))
                        .with(EmptyEntry.builder().weight(15));

                tableBuilder.pool(poolBuilder);
            }


            if (source.isBuiltin() && LootTables.TRIAL_CHAMBERS_CORRIDOR_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.STONE_MACHETE).weight(2)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 0.7F))))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 0.7F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(2, 10))
                                        .conditionally(RandomChanceLootCondition.builder(0.8F))))
                        .with(EmptyEntry.builder().weight(5));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.TRIAL_CHAMBERS_REWARD_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.19F))
                        .with(ItemEntry.builder(ModItems.DIAMOND_SAI).weight(4)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(20, 30))
                                        .conditionally(RandomChanceLootCondition.builder(0.9F))))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(6)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(15, 25))
                                        .conditionally(RandomChanceLootCondition.builder(0.95F))))
                        .with(ItemEntry.builder(Items.BOOK).weight(5)
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "scathe"))), UniformLootNumberProvider.create(1, 3))));
                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.115F))
                        .with(ItemEntry.builder(ModItems.DIAMOND_LONG_SWORD).weight(3)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(5, 40))))
                        .with(ItemEntry.builder(Items.BOOK).weight(2)
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "tear"))), UniformLootNumberProvider.create(2, 3)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(10, 25))
                                        .conditionally(RandomChanceLootCondition.builder(0.6F))))
                        .with(ItemEntry.builder(Items.BOOK).weight(2)
                                .apply(new EnchantRandomlyLootFunction.Builder().conditionally(RandomChanceLootCondition.builder(0.75F)))
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "scathe"))), UniformLootNumberProvider.create(1, 3)))
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "tear"))), UniformLootNumberProvider.create(1, 3))));

                tableBuilder.pool(poolBuilder);
            }

            // Ancient cities
            if (source.isBuiltin() && LootTables.ANCIENT_CITY_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DIAMOND_LONG_SWORD).weight(4)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.75F, 1)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(20, 35))
                                .conditionally(RandomChanceLootCondition.builder(0.9F))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_MACHETE).weight(3)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.6F, 0.8F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(25, 40))
                                        .conditionally(RandomChanceLootCondition.builder(0.6F))))
                        .with(ItemEntry.builder(Items.BOOK).weight(2)
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "scathe"))), UniformLootNumberProvider.create(1, 2))))
                        .with(ItemEntry.builder(Items.BOOK).weight(2)
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "tear"))), UniformLootNumberProvider.create(1, 3))))
                        .with(EmptyEntry.builder().weight(17));

                tableBuilder.pool(poolBuilder);
            }

            // Bastion remnants
            if (source.isBuiltin() && LootTables.BASTION_BRIDGE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.GOLDEN_LONG_SWORD).weight(2)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.4F, 0.7F))))
                        .with(EmptyEntry.builder().weight(23));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.BASTION_OTHER_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.GOLDEN_SAI).weight(9)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.7F, 0.9F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(15, 35))
                                        .conditionally(RandomChanceLootCondition.builder(0.7F))))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(4)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.7F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(2, 10))
                                        .conditionally(RandomChanceLootCondition.builder(0.6F))))
                        .with(EmptyEntry.builder().weight(38));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.BASTION_HOGLIN_STABLE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.GOLDEN_MACHETE)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.5F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(1, 5))
                                        .conditionally(RandomChanceLootCondition.builder(0.3F))))
                        .with(EmptyEntry.builder().weight(4));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.BASTION_TREASURE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(3))
                        .with(ItemEntry.builder(ModItems.GOLDEN_SAI).weight(10)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.65F, 1)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(25, 35))
                                        .conditionally(RandomChanceLootCondition.builder(0.37F))))
                        .with(EmptyEntry.builder().weight(13));

                tableBuilder.pool(poolBuilder);

                LootPool.Builder poolBuilder2 = LootPool.builder().rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.DIAMOND_LONG_SWORD).weight(3)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.8F, 1)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(10, 35))
                                        .conditionally(RandomChanceLootCondition.builder(0.6F))))
                        .with(ItemEntry.builder(ModItems.NETHERITE_MACHETE).weight(2)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(1, 7))
                                        .conditionally(RandomChanceLootCondition.builder(0.2F)))
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.85F, 1))))
                        .with(EmptyEntry.builder().weight(9));

                tableBuilder.pool(poolBuilder2);
            }

            // Buried treasures
            if (source.isBuiltin() && LootTables.BURIED_TREASURE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(14))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(13))
                        .with(EmptyEntry.builder().weight(73));

                tableBuilder.pool(poolBuilder);
            }

            // End cities
            if (source.isBuiltin() && LootTables.END_CITY_TREASURE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(5)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(15, 40))))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(5)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(15, 40))))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(5)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(15, 40))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_MACHETE).weight(6)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(20, 35))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_LONG_SWORD).weight(6)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(20, 35))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_SAI).weight(6)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(20, 35))))
                        .with(EmptyEntry.builder().weight(167));

                tableBuilder.pool(poolBuilder);
            }

            // Igloos
            if (source.isBuiltin() && LootTables.IGLOO_CHEST_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.STONE_LONG_SWORD).weight(7)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 1))))
                        .with(EmptyEntry.builder().weight(43));

                tableBuilder.pool(poolBuilder);
            }

            // Jungle temples
            if (source.isBuiltin() && LootTables.JUNGLE_TEMPLE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DIAMOND_MACHETE).weight(6))
                        .with(ItemEntry.builder(ModItems.WOODEN_MACHETE).weight(23)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 1))))
                        .with(EmptyEntry.builder().weight(71));

                tableBuilder.pool(poolBuilder);
            }

            // Nether fortresses
            if (source.isBuiltin() && LootTables.NETHER_BRIDGE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.GOLDEN_LONG_SWORD))
                        .with(EmptyEntry.builder().weight(4));

                tableBuilder.pool(poolBuilder);
            }

            // Pillager outposts
            if (source.isBuiltin() && LootTables.PILLAGER_OUTPOST_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.WOODEN_MACHETE).weight(8)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 1))))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(12)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.8F, 1))))
                        .with(ItemEntry.builder(ModItems.STONE_MACHETE).weight(15)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.7F, 1))))
                        .with(EmptyEntry.builder().weight(65));

                tableBuilder.pool(poolBuilder);
            }

            // Ruined portals
            if (source.isBuiltin() && LootTables.RUINED_PORTAL_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.GOLDEN_MACHETE)
                                .apply(EnchantRandomlyLootFunction.create()))
                        .with(ItemEntry.builder(ModItems.GOLDEN_LONG_SWORD)
                                .apply(EnchantRandomlyLootFunction.create()))
                        .with(ItemEntry.builder(ModItems.GOLDEN_SAI)
                                .apply(EnchantRandomlyLootFunction.create()))
                        .with(EmptyEntry.builder().weight(11));

                tableBuilder.pool(poolBuilder);
            }

            // shipwrecks
            if (source.isBuiltin() && LootTables.SHIPWRECK_SUPPLY_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WOODEN_LONG_SWORD).weight(21)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 1)))
                                .apply(EnchantRandomlyLootFunction.create()))
                        .with(EmptyEntry.builder().weight(83));

                tableBuilder.pool(poolBuilder);

                LootPool.Builder poolBuilder2 = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.16F))
                        .rolls(UniformLootNumberProvider.create(1, 3))
                        .with(ItemEntry.builder(ModItems.WOODEN_SAI)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 1)))
                                .apply(EnchantRandomlyLootFunction.create()));

                tableBuilder.pool(poolBuilder2);
            }

            if (source.isBuiltin() && LootTables.SHIPWRECK_TREASURE_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(5)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.3F, 1)))
                                .apply(EnchantRandomlyLootFunction.create().conditionally(RandomChanceLootCondition.builder(0.63F))))
                        .with(EmptyEntry.builder().weight(16));

                tableBuilder.pool(poolBuilder);

                LootPool.Builder poolBuilder2 = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.34F))
                        .rolls(UniformLootNumberProvider.create(1, 3))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(22)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(2, 15))
                                        .conditionally(RandomChanceLootCondition.builder(0.17F))))
                        .with(ItemEntry.builder(ModItems.GOLDEN_SAI).weight(12)
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(2, 15))
                                .conditionally(RandomChanceLootCondition.builder(0.17F))))
                        .with(EmptyEntry.builder().weight(16));

                tableBuilder.pool(poolBuilder2);
            }

            // Strongholds
            if (source.isBuiltin() && LootTables.STRONGHOLD_CORRIDOR_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(13))
                        .with(EmptyEntry.builder().weight(87));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.STRONGHOLD_LIBRARY_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(3))
                        .with(ItemEntry.builder(Items.BOOK)
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "scathe"))), UniformLootNumberProvider.create(1, 3))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .apply(new SetEnchantmentsLootFunction.Builder(true).enchantment(listener.getWrapperOrThrow(
                                        RegistryKeys.ENCHANTMENT).getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier
                                        .of("exoticarmaments", "tear"))), UniformLootNumberProvider.create(1, 3))))
                        .with(EmptyEntry.builder().weight(27));

                tableBuilder.pool(poolBuilder);
            }

            // Underwater ruins
            if (source.isBuiltin() && LootTables.UNDERWATER_RUIN_SMALL_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.18F))
                        .rolls(UniformLootNumberProvider.create(1, 2))
                        .with(ItemEntry.builder(ModItems.STONE_SAI).weight(9))
                        .with(ItemEntry.builder(ModItems.WOODEN_MACHETE).weight(7)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.4F, 0.75F))));

                tableBuilder.pool(poolBuilder);
            }

            // Woodland mansions
            if (source.isBuiltin() && LootTables.WOODLAND_MANSION_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(15)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(10)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.8F, 1)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(15, 25))
                                        .conditionally(RandomChanceLootCondition.builder(0.7F))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_SAI).weight(5)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.85F, 0.99F))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_SAI).weight(4)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.25F, 0.6F))))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(7)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.35F, 1)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(10, 25))
                                        .conditionally(RandomChanceLootCondition.builder(0.9F))))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(8)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.2F, 0.6F))))
                        .with(EmptyEntry.builder().weight(51));

                tableBuilder.pool(poolBuilder);
            }

            // Villages
            if (source.isBuiltin() && LootTables.VILLAGE_WEAPONSMITH_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.496F))
                        .rolls(UniformLootNumberProvider.create(1, 2))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(35))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(17))
                        .with(ItemEntry.builder(ModItems.IRON_SAI).weight(10)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.VILLAGE_TOOLSMITH_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(4)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))))
                        .with(EmptyEntry.builder().weight(13));

                tableBuilder.pool(poolBuilder);
            }

            // Bonus chest
            if (source.isBuiltin() && LootTables.SPAWN_BONUS_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WOODEN_MACHETE).weight(2))
                        .with(EmptyEntry.builder());

                tableBuilder.pool(poolBuilder);

                LootPool.Builder poolBuilder2 = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.STONE_MACHETE).weight(2))
                        .with(EmptyEntry.builder().weight(9));

                tableBuilder.pool(poolBuilder2);
            }

            // Bartering
            if (source.isBuiltin() && LootTables.PIGLIN_BARTERING_GAMEPLAY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.05F))
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD).weight(5)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.7F, 0.8F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(18, 30))))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(3)
                                .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.6F, 0.9F)))
                                .apply(new EnchantWithLevelsLootFunction.Builder(UniformLootNumberProvider.create(18, 30))));

                tableBuilder.pool(poolBuilder);
            }

            // Gifts
            if (source.isBuiltin() && LootTables.HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT_GAMEPLAY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.2F))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.GOLDEN_SAI))
                        .with(ItemEntry.builder(ModItems.IRON_SAI)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,2))))
                        .with(ItemEntry.builder(ModItems.IRON_LONG_SWORD))
                        .with(ItemEntry.builder(ModItems.STONE_LONG_SWORD));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && LootTables.HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT_GAMEPLAY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().conditionally(RandomChanceLootCondition.builder(0.2F))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.IRON_MACHETE).weight(10))
                        .with(ItemEntry.builder(ModItems.STONE_MACHETE).weight(10)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,2))))
                        .with(ItemEntry.builder(ModItems.DIAMOND_MACHETE));

                tableBuilder.pool(poolBuilder);
            }
        });
    }

}
