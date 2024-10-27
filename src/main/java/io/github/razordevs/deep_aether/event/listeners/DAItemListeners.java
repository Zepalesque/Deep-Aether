package io.github.razordevs.deep_aether.event.listeners;

import com.aetherteam.aether.event.hooks.ItemHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class DAItemListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(EventPriority.LOWEST, DAItemListeners::onTooltipAdd);
    }

    /**
     * @see DAItemHooks#addDungeonTooltips(List, ItemStack, TooltipFlag)
     */
    public static void onTooltipAdd(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        TooltipFlag tooltipFlag = event.getFlags();
        List<Component> itemTooltips = event.getToolTip();
        ItemHooks.addDungeonTooltips(itemTooltips, itemStack, tooltipFlag);
    }
}
