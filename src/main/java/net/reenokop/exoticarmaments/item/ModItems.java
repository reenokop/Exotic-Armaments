package net.reenokop.exoticarmaments.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item register(Item item, String id) {
        return Registry.register(Registries.ITEM, Identifier.of("exoticarmaments", id), item);
    }

    public static void initializeItems() { }

    // Machetes!
    public static final Item WOODEN_MACHETE = register(new MacheteItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(MacheteItem.createAttributeModifiers(ToolMaterials.WOOD, 2, -2.2F, 18)), 1.4F, 1.4F, 18), "wooden_machete");
    public static final Item STONE_MACHETE = register(new MacheteItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(MacheteItem.createAttributeModifiers(ToolMaterials.STONE, 2, -2.2F, 24)), 2.15F, 2.55F, 24), "stone_machete");
    public static final Item IRON_MACHETE = register(new MacheteItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(MacheteItem.createAttributeModifiers(ToolMaterials.IRON, 2, -2.2F, 27)), 2.9F, 3.7F, 27), "iron_machete");
    public static final Item GOLDEN_MACHETE = register(new MacheteItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(MacheteItem.createAttributeModifiers(ToolMaterials.GOLD, 2, -2.2F, 21)), 5.15F, 7.15F, 21), "golden_machete");
    public static final Item DIAMOND_MACHETE = register(new MacheteItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(MacheteItem.createAttributeModifiers(ToolMaterials.DIAMOND, 2, -2.2F, 30)), 3.65F, 4.85F, 30), "diamond_machete");
    public static final Item NETHERITE_MACHETE = register(new MacheteItem(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(MacheteItem.createAttributeModifiers(ToolMaterials.NETHERITE, 2, -2.2F, 33)), 4.0F, 5.4F, 33), "netherite_machete");

    // Long Swords!
    public static final Item WOODEN_LONG_SWORD = register(new LongSwordItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(LongSwordItem.createAttributeModifiers(ToolMaterials.WOOD, 4, -2.7F))), "wooden_long_sword");
    public static final Item STONE_LONG_SWORD = register(new LongSwordItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(LongSwordItem.createAttributeModifiers(ToolMaterials.STONE, 4, -2.7F))), "stone_long_sword");
    public static final Item IRON_LONG_SWORD = register(new LongSwordItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(LongSwordItem.createAttributeModifiers(ToolMaterials.IRON, 4, -2.7F))), "iron_long_sword");
    public static final Item GOLDEN_LONG_SWORD = register(new LongSwordItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(LongSwordItem.createAttributeModifiers(ToolMaterials.GOLD, 4, -2.7F))), "golden_long_sword");
    public static final Item DIAMOND_LONG_SWORD = register(new LongSwordItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(LongSwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 4, -2.7F))), "diamond_long_sword");
    public static final Item NETHERITE_LONG_SWORD = register(new LongSwordItem(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(LongSwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.7F))), "netherite_long_sword");

    // Sais!
    public static final Item WOODEN_SAI = register(new SaiItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(SaiItem.createAttributeModifiers(ToolMaterials.WOOD, 1, -2.1F, 2, 3.2F)), 2, 3.2F, 47, 31), "wooden_sai");
    public static final Item STONE_SAI = register(new SaiItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(SaiItem.createAttributeModifiers(ToolMaterials.STONE, 0.5F, -2.1F, 3, 3.2F)), 3, 3.2F, 56, 35), "stone_sai");
    public static final Item IRON_SAI = register(new SaiItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(SaiItem.createAttributeModifiers(ToolMaterials.IRON, -0.5F, -1.8F, 2.5F, 3.7F)), 2.5F, 3.7F, 74, 38), "iron_sai");
    public static final Item GOLDEN_SAI = register(new SaiItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(SaiItem.createAttributeModifiers(ToolMaterials.GOLD, 1, -2.1F, 1.5F, 3.2F)), 1.5F, 3.2F, 63, 44), "golden_sai");
    public static final Item DIAMOND_SAI = register(new SaiItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(SaiItem.createAttributeModifiers(ToolMaterials.DIAMOND, -1, -1.8F, 3.5F, 3.7F)), 3.5F, 3.7F, 87, 43), "diamond_sai");
    public static final Item NETHERITE_SAI = register(new SaiItem(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(SaiItem.createAttributeModifiers(ToolMaterials.NETHERITE, -1.5F, -1.8F, 3.5F, 3.7F)), 3.5F, 3.7F, 98, 46), "netherite_sai");

}
