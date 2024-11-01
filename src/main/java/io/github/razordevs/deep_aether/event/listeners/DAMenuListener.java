package io.github.razordevs.deep_aether.event.listeners;

import com.aetherteam.cumulus.client.CumulusClient;
import io.github.razordevs.deep_aether.init.DAMenus;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class DAMenuListener {
    public static void listen(IEventBus bus) {
        bus.addListener(EventPriority.HIGHEST, DAMenuListener::onGuiOpenHighest);
    }

    public static void onGuiOpenHighest(ScreenEvent.Opening event) {
        CumulusClient.MENU_HELPER.prepareMenu(DAMenus.DEEP_AETHER.get());
        CumulusClient.MENU_HELPER.prepareMenu(DAMenus.DEEP_AETHER_LEFT.get());
    }
}
