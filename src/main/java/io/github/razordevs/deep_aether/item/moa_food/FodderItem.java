package io.github.razordevs.deep_aether.item.moa_food;

import com.aetherteam.aether.entity.passive.Moa;
import io.github.razordevs.deep_aether.item.component.DADataComponentTypes;
import io.github.razordevs.deep_aether.item.component.MoaFodder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FodderItem extends Item {


    public FodderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if(!(livingEntity instanceof Moa))
            return InteractionResult.FAIL;

        if(!player.isCreative())
            itemStack.shrink(1);

        if(applyMoaEffect(livingEntity, itemStack)) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.isPassenger())
            if(pPlayer.getVehicle() instanceof Moa moa){
                if(!pPlayer.isCreative())
                    pPlayer.getItemInHand(pUsedHand).shrink(1);
                applyMoaEffect(moa, pPlayer.getItemInHand(pUsedHand));
                return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
            }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        MoaFodder fodder = stack.get(DADataComponentTypes.MOA_FODDER);
        if(fodder != null)
            PotionContents.addPotionTooltip(List.of(fodder.effect()), tooltipComponents::add, 1.0F, context.level() == null ? 20.0F : context.tickRate());
    }

    private boolean applyMoaEffect(LivingEntity livingEntity, ItemStack stack) {
        MoaFodder fodder = stack.get(DADataComponentTypes.MOA_FODDER);
        if(fodder != null) {
            if (livingEntity.addEffect(fodder.effect())) {
                livingEntity.level().playLocalSound(livingEntity, SoundEvents.PLAYER_BURP, SoundSource.AMBIENT, 1f, 0.2f);
                return true;
            }
        }
        return false;
    }
}
