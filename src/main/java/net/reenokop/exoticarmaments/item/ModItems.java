package net.reenokop.exoticarmaments.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item register(Item item, String id) {
        return Registry.register(Registries.ITEM, Identifier.of("exoticarmaments", id), item);
    }

    public static void initializeItems() { }

    // Machetes!
    public static final Item WOODEN_MACHETE = register(new MacheteItem(ToolMaterial.WOOD, 2, -2.2F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "wooden_machete"))), 1.4F, 1.4F,18), "wooden_machete");
    public static final Item STONE_MACHETE = register(new MacheteItem(ToolMaterial.STONE, 2, -2.2F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "stone_machete"))), 2.15F, 2.55F, 24), "stone_machete");
    public static final Item IRON_MACHETE = register(new MacheteItem(ToolMaterial.IRON, 2, -2.2F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "iron_machete"))), 2.9F, 3.7F, 27), "iron_machete");
    public static final Item GOLDEN_MACHETE = register(new MacheteItem(ToolMaterial.GOLD, 2, -2.2F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "golden_machete"))), 5.15F, 7.15F, 21), "golden_machete");
    public static final Item DIAMOND_MACHETE = register(new MacheteItem(ToolMaterial.DIAMOND, 2, -2.2F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "diamond_machete"))), 3.65F, 4.85F, 30), "diamond_machete");
    public static final Item NETHERITE_MACHETE = register(new MacheteItem(ToolMaterial.NETHERITE, 2, -2.2F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "netherite_machete"))), 4.0F, 5.4F, 33), "netherite_machete");

    // Long Swords!
    public static final Item WOODEN_LONG_SWORD = register(new LongSwordItem(ToolMaterial.WOOD, 4, -2.7F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "wooden_long_sword")))), "wooden_long_sword");
    public static final Item STONE_LONG_SWORD = register(new LongSwordItem(ToolMaterial.STONE, 4, -2.7F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "stone_long_sword")))), "stone_long_sword");
    public static final Item IRON_LONG_SWORD = register(new LongSwordItem(ToolMaterial.IRON, 4, -2.7F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "iron_long_sword")))), "iron_long_sword");
    public static final Item GOLDEN_LONG_SWORD = register(new LongSwordItem(ToolMaterial.GOLD, 4, -2.7F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "golden_long_sword")))), "golden_long_sword");
    public static final Item DIAMOND_LONG_SWORD = register(new LongSwordItem(ToolMaterial.DIAMOND, 4, -2.7F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "diamond_long_sword")))), "diamond_long_sword");
    public static final Item NETHERITE_LONG_SWORD = register(new LongSwordItem(ToolMaterial.NETHERITE, 4, -2.7F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "netherite_long_sword")))), "netherite_long_sword");

    // Sais!
    public static final Item WOODEN_SAI = register(new SaiItem(ToolMaterial.WOOD, 1, -2.1F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "wooden_sai"))), 2, 3.2F, 47, 31), "wooden_sai");
    public static final Item STONE_SAI = register(new SaiItem(ToolMaterial.STONE, 0.5F, -2.1F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "stone_sai"))), 3, 3.2F, 56, 35), "stone_sai");
    public static final Item IRON_SAI = register(new SaiItem(ToolMaterial.IRON, -0.5F, -1.8F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "iron_sai"))), 2.5F, 3.7F, 74, 38), "iron_sai");
    public static final Item GOLDEN_SAI = register(new SaiItem(ToolMaterial.GOLD, 1, -2.1F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "golden_sai"))), 1.5F, 3.2F, 63, 44), "golden_sai");
    public static final Item DIAMOND_SAI = register(new SaiItem(ToolMaterial.DIAMOND, -1, -1.8F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "diamond_sai"))), 3.5F, 3.7F, 87, 43), "diamond_sai");
    public static final Item NETHERITE_SAI = register(new SaiItem(ToolMaterial.NETHERITE, -1.5F, -1.8F, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of("exoticarmaments", "netherite_sai"))), 3.5F, 3.7F, 98, 46), "netherite_sai");

}
