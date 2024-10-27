package io.github.razordevs.deep_aether.item.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import io.github.razordevs.deep_aether.item.gear.other.FlawlessDrop;

import java.util.List;

public class SunCore extends Item implements FlawlessDrop {
    public SunCore(Properties properties) {
        super(properties);
    }

    int i = 0;
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        flawlessComponent(tooltipComponents, i);
        i = i < 80 ? i + 1 : 0;
    }
}
