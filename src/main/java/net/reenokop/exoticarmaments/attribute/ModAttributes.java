package net.reenokop.exoticarmaments.attribute;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModAttributes {

    // The functionality is implemented independently. Most of these are only for tooltips!
    public static final Identifier SHIELD_DISABLE_CHANCE_MODIFIER_ID = Identifier.of("shield_disable_chance");

    public static final RegistryEntry<EntityAttribute> SHIELD_DISABLE_CHANCE =
            Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("exoticarmaments",
                    "attribute.exoticarmaments.generic.shield_disable_chance"), new ClampedEntityAttribute(
                    "attribute.exoticarmaments.generic.shield_disable_chance", 0, 0, 2048));


    public static final Identifier SWEEPING_EDGE_RADIUS_MODIFIER_ID = Identifier.of("sweeping_edge_radius");

    public static final RegistryEntry<EntityAttribute> SWEEPING_EDGE_RADIUS =
            Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("exoticarmaments",
                    "attribute.exoticarmaments.generic.sweeping_edge_radius"), new ClampedEntityAttribute(
                    "attribute.exoticarmaments.generic.sweeping_edge_radius", 0, 0, 2048));


    public static final Identifier OFFHAND_ATTACK_DAMAGE_MODIFIER_ID = Identifier.of("offhand_attack_damage");

    public static final RegistryEntry<EntityAttribute> OFFHAND_ATTACK_DAMAGE =
            Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("exoticarmaments",
                    "attribute.exoticarmaments.generic.offhand_attack_damage"), new ClampedEntityAttribute(
                            "attribute.exoticarmaments.generic.offhand_attack_damage", 0, 0, 2048));


    public static final Identifier DUAL_WIELD_ATTACK_SPEED_MODIFIER_ID = Identifier.of("dual_wield_attack_speed");

    public static final RegistryEntry<EntityAttribute> DUAL_WIELD_ATTACK_SPEED =
            Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("exoticarmaments",
                    "attribute.exoticarmaments.generic.dual_wield_attack_speed"), new ClampedEntityAttribute(
                    "attribute.exoticarmaments.generic.dual_wield_attack_speed", 0, 0, 2048));


    public static final Identifier PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.ofVanilla("entity_interaction_range");

}
