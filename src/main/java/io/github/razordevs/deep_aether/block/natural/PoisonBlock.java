package io.github.razordevs.deep_aether.block.natural;

import com.aetherteam.aether.effect.AetherEffects;
import io.github.razordevs.deep_aether.advancement.PoisonTrigger;
import io.github.razordevs.deep_aether.init.DAParticles;
import io.github.razordevs.deep_aether.recipe.DARecipeTypes;
import io.github.razordevs.deep_aether.recipe.poison.PoisonRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;

public class PoisonBlock extends LiquidBlock {
    //Used as a timer, to indicate when the position recipe is finished.
    boolean doCount = false;
    int conversionTime = 0;

    public PoisonBlock(FlowingFluid deferredHolder, Properties properties) {
        super(deferredHolder, properties);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        ((LivingEntity) entity).addEffect(new MobEffectInstance(AetherEffects.INEBRIATION, 100, 0, false, false));
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

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (doCount && conversionTime < 200) {
            conversionTime++;
        }else {
            conversionTime = 0;
            doCount = false;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    /**
     * Used to apply inebriation effect to entities and convert items if they have a recipe
     * See {@link DARecipeTypes} for poison recipe serializer
     */
    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide()) return;

        //If we're not dealing with an ItemEntity, apply Inebriation and return.
        if (!(entity instanceof ItemEntity itemEntity)) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(AetherEffects.INEBRIATION, 100, 0, false, false));
            return;
        }

        //Poison recipe code
        //Temporary initialization for the result item of the poison recipe
        ItemStack TRANSFORM_ITEM = ItemStack.EMPTY;

        //Checks if any poison recipe matches the ingredient
        for (RecipeHolder<PoisonRecipe> recipe : level.getRecipeManager().getAllRecipesFor(DARecipeTypes.POISON_RECIPE.get())) {
            if (recipe.value().getIngredients().getFirst().getItems()[0].is(itemEntity.getItem().getItem())) {
                TRANSFORM_ITEM = recipe.value().getResult();

                //Starts the timer in the randomTick function.
                this.doCount = true;
            }
        }

        if(TRANSFORM_ITEM.isEmpty() || !itemEntity.isAlive()) return;

        TRANSFORM_ITEM.setCount(itemEntity.getItem().getCount());

        //We spawn particles around the ingredient to indicate that the ingredient is getting converted.
        BlockPos itemPos = itemEntity.getOnPos();
        ServerLevel serverlevel = (ServerLevel) level;

        serverlevel.sendParticles(DAParticles.POISON_BUBBLES.get(), (double) itemPos.getX() + level.random.nextDouble(), pos.getY() + 1, (double) itemPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.2D, 0.3D);
        if (level.random.nextInt(25) == 0)
            serverlevel.playSound(itemEntity, itemPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.2F + level.random.nextFloat() * 0.2F, 0.9F + level.random.nextFloat() * 0.15F);

        //Converts the ingredient when enough time has passed and the entity still is alive.
        if (conversionTime > 2) {

            //Stops the timer
            this.doCount = false;
            //Grants the "Purple Magic" advancement.
            if (itemEntity.getOwner() instanceof ServerPlayer player) {
                PoisonTrigger.INSTANCE.trigger(player, itemEntity.getItem());
            }

            //Removes the ingredient item and spawns the result item
            itemEntity.discard();
            entity.spawnAtLocation(TRANSFORM_ITEM, 0);
            entity.setNoGravity(true);
        }
    }
}

