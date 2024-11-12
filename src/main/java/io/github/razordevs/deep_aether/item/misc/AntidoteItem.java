package io.github.razordevs.deep_aether.item.misc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class AntidoteItem extends Item {
    private final boolean enchanted;

    public AntidoteItem(boolean enchanted, Properties properties) {
        super(properties);
        this.enchanted = enchanted;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return enchanted;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}
