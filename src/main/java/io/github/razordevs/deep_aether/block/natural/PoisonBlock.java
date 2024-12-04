package io.github.razordevs.deep_aether.block.natural;

import com.aetherteam.aether.effect.AetherEffects;
import io.github.razordevs.deep_aether.entity.PoisonItem;
import io.github.razordevs.deep_aether.init.DAParticles;
import io.github.razordevs.deep_aether.recipe.DARecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;

public class PoisonBlock extends LiquidBlock {
    public PoisonBlock(FlowingFluid deferredHolder, Properties properties) {
        super(deferredHolder, properties);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(AetherEffects.INEBRIATION, 100, 0, false, false));
        }
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter level, BlockPos pos, FluidState fluidState) {
        return true;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        double d0 = blockPos.getX();
        double d1 = blockPos.getY();
        double d2 = blockPos.getZ();
        level.addAlwaysVisibleParticle(DAParticles.POISON_BUBBLES.get(), d0 + (double) randomSource.nextFloat(), d1 + (double) randomSource.nextFloat(), d2 + (double) randomSource.nextFloat(), 0.0D, 0.04D, 0.0D);
        if (randomSource.nextInt(10) == 0) {
            level.playLocalSound(d0, d1, d2, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 0.2F + randomSource.nextFloat() * 0.2F, 0.9F + randomSource.nextFloat() * 0.15F, false);
        }
        super.animateTick(blockState, level, blockPos, randomSource);
    }

    /**
     * Used to apply inebriation effect to entities and convert items if they have a recipe
     * See {@link DARecipeTypes} for poison recipe serializer
     */
    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        //Applies inebriation effect to living entities
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(AetherEffects.INEBRIATION, 100, 0, false, false));
        }

        //Poison recipe code
        else if (entity instanceof PoisonItem poisonItem) {
            poisonItem.deep_Aether$increaseTime();

            if(poisonItem.deep_Aether$canConvert()) {
                ItemEntity itemEntity = (ItemEntity) poisonItem;
                if (!level.isClientSide && itemEntity.isAlive()) {
                    BlockPos itemPos = itemEntity.getOnPos();
                    ServerLevel serverlevel = (ServerLevel) level;
                    serverlevel.sendParticles(DAParticles.POISON_BUBBLES.get(), (double) itemPos.getX() + level.random.nextDouble(), pos.getY() + 1, (double) itemPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.2D, 0.3D);
                    if (level.random.nextInt(25) == 0) {
                        serverlevel.playSound(itemEntity, itemPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.2F + level.random.nextFloat() * 0.2F, 0.9F + level.random.nextFloat() * 0.15F);
                    }
                }
            }
        }
    }
}

