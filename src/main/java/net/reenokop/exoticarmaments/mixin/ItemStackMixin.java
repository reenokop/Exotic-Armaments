package net.reenokop.exoticarmaments.mixin;

import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.reenokop.exoticarmaments.attribute.ModAttributes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    // Fancy pants tooltips
    @Inject(method = "appendAttributeModifierTooltip", at = @At("HEAD"), cancellable = true)
    public void appendCustomAttributeModifierTooltip(Consumer<Text> textConsumer, @Nullable PlayerEntity player,
            RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, CallbackInfo ci) {

        if (player != null) {

            if (modifier.idMatches(ModAttributes.SHIELD_DISABLE_CHANCE_MODIFIER_ID)) {
                textConsumer.accept(ScreenTexts.space().append(Text.translatable("attribute.modifier.equals.1",
                        AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                        Text.translatable(attribute.value().getTranslationKey()))).formatted(Formatting.GREEN));
                ci.cancel();
            }

            if (modifier.idMatches(ModAttributes.SWEEPING_EDGE_RADIUS_MODIFIER_ID)) {
                textConsumer.accept(ScreenTexts.space().append(Text.translatable("attribute.modifier.plus.0",
                        AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                        Text.translatable(attribute.value().getTranslationKey()))).formatted(Formatting.AQUA));
                ci.cancel();
            }

            if (modifier.idMatches(ModAttributes.PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID)) {
                textConsumer.accept(ScreenTexts.space().append(Text.translatable("attribute.modifier.plus.0",
                        AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                        Text.translatable(attribute.value().getTranslationKey()))).formatted(Formatting.BLUE));
                ci.cancel();
            }

            if (modifier.idMatches(ModAttributes.OFFHAND_ATTACK_DAMAGE_MODIFIER_ID)) {
                textConsumer.accept(ScreenTexts.space().append(Text.translatable("attribute.modifier.equals.0",
                        AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                        Text.translatable(attribute.value().getTranslationKey()))).formatted(Formatting.DARK_BLUE));
                ci.cancel();
            }

            if (modifier.idMatches(ModAttributes.DUAL_WIELD_ATTACK_SPEED_MODIFIER_ID)) {
                textConsumer.accept(ScreenTexts.space().append(Text.translatable("attribute.modifier.equals.0",
                        AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()),
                        Text.translatable(attribute.value().getTranslationKey()))).formatted(Formatting.DARK_AQUA));
                ci.cancel();
            }
        }
    }

}
