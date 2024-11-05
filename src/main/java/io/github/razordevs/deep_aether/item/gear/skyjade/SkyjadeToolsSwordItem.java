
package io.github.razordevs.deep_aether.item.gear.skyjade;

import com.aetherteam.aether.item.combat.abilities.weapon.ZaniteWeapon;
import com.google.common.collect.Multimap;
import io.github.razordevs.deep_aether.DeepAetherConfig;
import io.github.razordevs.deep_aether.block.misc.DisableSound;
import io.github.razordevs.deep_aether.init.DATiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class SkyjadeToolsSwordItem extends SwordItem implements SkyjadeTool, SkyjadeWeapon {
	public SkyjadeToolsSwordItem() {
		super(DATiers.SKYJADE, new Properties().attributes(SwordItem.createAttributes(DATiers.SKYJADE, 3, -3f)));
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		this.disableSound(player, pos);
		return super.canAttackBlock(state, level, pos, player);
	}

	@Override
	public boolean isEnchantable(ItemStack itemStack) {
		return DeepAetherConfig.COMMON.skyjade_enchant.get() && !DeepAetherConfig.COMMON.enable_skyjade_rework.get();
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return DeepAetherConfig.COMMON.skyjade_enchant.get() && !DeepAetherConfig.COMMON.enable_skyjade_rework.get();
	}
}
