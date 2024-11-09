package io.github.razordevs.deep_aether.item.gear.other;

import com.aetherteam.aether.item.accessories.cape.CapeItem;
import io.github.razordevs.deep_aether.entity.BabyEots;
import io.github.razordevs.deep_aether.item.component.DADataComponentTypes;
import io.github.razordevs.deep_aether.item.component.FloatyScarf;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FloatyScarfItem extends CapeItem implements FlawlessDrop {
    public FloatyScarfItem(String capeLocation, Properties properties) {
        super(capeLocation, properties);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        if(reference.entity() instanceof Player player) {
            tryAddBabyEots(stack, player);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        tryDiscardBabyEots(stack, reference.entity().level());
    }



    public static void tryDiscardBabyEots(@Nullable ItemStack stack, Level level) {
        if(stack == null)
            return;

        FloatyScarf scarf = stack.get(DADataComponentTypes.FLOATY_SCARF);
        if(scarf != null) {
            Entity entity = level.getEntity(scarf.uuid());
            if(entity != null)
                entity.discard();
        }
    }

    public static void tryAddBabyEots(@Nullable ItemStack stack, Player player) {
        if(stack == null)
            return;

        FloatyScarf scarf = stack.get(DADataComponentTypes.FLOATY_SCARF);
        if(scarf != null) {
            BabyEots eots = new BabyEots(player.level(), player);
            stack.set(DADataComponentTypes.FLOATY_SCARF, new FloatyScarf(eots.getId(), 0, 0, 0, 0));
        }
    }

    int i = 0;
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        flawlessComponent(tooltipComponents, i);
        i = i < 80 ? i + 1 : 0;
    }
}
