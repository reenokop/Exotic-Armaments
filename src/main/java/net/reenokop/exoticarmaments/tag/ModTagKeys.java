package net.reenokop.exoticarmaments.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTagKeys {

    public static final TagKey<Block> MACHETE_EFFICIENT = TagKey
            .of(RegistryKeys.BLOCK, Identifier.of("exoticarmaments", "machete_efficient"));

}
