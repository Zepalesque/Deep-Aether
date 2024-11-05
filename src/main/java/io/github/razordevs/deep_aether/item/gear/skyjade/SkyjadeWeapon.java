package io.github.razordevs.deep_aether.item.gear.skyjade;

import io.github.razordevs.deep_aether.DeepAether;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.concurrent.atomic.AtomicReference;

public interface SkyjadeWeapon {
    ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "skyjade_weapon_attack_damage");

    default ItemAttributeModifiers.Entry increaseDamage(ItemAttributeModifiers modifiers, ItemStack stack) {
        return new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, this.calculateIncrease(modifiers, stack), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    private int calculateIncrease(ItemAttributeModifiers modifiers, ItemStack stack) {
        AtomicReference<Double> baseStat = new AtomicReference<>(0.0);
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == Attributes.ATTACK_DAMAGE.value() && !modifier.id().equals(SkyjadeWeapon.DAMAGE_MODIFIER_ID)) {
                baseStat.updateAndGet((v) -> v + modifier.amount());
            }

        });
        double boostedDamage = baseStat.get() / (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
        boostedDamage -= baseStat.get();
        if (boostedDamage < 0.0) {
            boostedDamage = 0.0;
        }
        return (int) Math.round(boostedDamage);
    }
}