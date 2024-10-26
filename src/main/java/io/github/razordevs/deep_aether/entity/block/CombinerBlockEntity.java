package io.github.razordevs.deep_aether.entity.block;

import com.google.common.collect.Lists;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.block.utility.CombinerBlock;
import io.github.razordevs.deep_aether.init.DABlockEntityTypes;
import io.github.razordevs.deep_aether.init.DABlocks;
import io.github.razordevs.deep_aether.recipe.DARecipeTypes;
import io.github.razordevs.deep_aether.recipe.combiner.CombinerRecipe;
import io.github.razordevs.deep_aether.recipe.combiner.CombinerRecipeInput;
import io.github.razordevs.deep_aether.screen.CombinerMenu;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CombinerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4);

    private static final int FIRST_SLOT = 0;
    private static final int SECOND_SLOT = 1;
    private static final int THIRD_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> CombinerBlockEntity.this.processingProgress;
                case 1 -> CombinerBlockEntity.this.processingTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> CombinerBlockEntity.this.processingProgress = pValue;
                case 1 -> CombinerBlockEntity.this.processingTotalTime = pValue;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    int processingProgress = 0;
    int processingTotalTime = 78;
    boolean combining;
    int combiningDuration;

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<CombinerRecipeInput, CombinerRecipe> quickCheck;

    public CombinerBlockEntity() {
        super(DABlockEntityTypes.COMBINER.get(), BlockPos.ZERO, DABlocks.COMBINER.get().defaultBlockState());
    }

    public CombinerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(DABlockEntityTypes.COMBINER.get(), pPos, pBlockState);
    }

    public CombinerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.quickCheck = RecipeManager.createCheck(DARecipeTypes.COMBINING.get());
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block." + DeepAether.MODID + ".combiner");
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block." + DeepAether.MODID + ".combiner");
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory) {
        return new CombinerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registry) {
        super.loadAdditional(tag, registry);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registry);
        this.processingProgress = tag.getInt("ProcessingTime");
        this.processingTotalTime = tag.getInt("ProcessingTimeTotal");
        CompoundTag recipesUsedTag = tag.getCompound("RecipesUsed");
        for (String key : recipesUsedTag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.tryParse(key), recipesUsedTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registry) {
        super.saveAdditional(tag, registry);
        tag.putInt("ProcessingTime", this.processingProgress);
        tag.putInt("ProcessingTimeTotal", this.processingTotalTime);
        ContainerHelper.saveAllItems(tag, this.items, registry);
        CompoundTag recipesUsedTag = new CompoundTag();
        this.recipesUsed.forEach((location, integer) -> recipesUsedTag.putInt(location.toString(), integer));
        tag.put("RecipesUsed", recipesUsedTag);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CombinerBlockEntity blockEntity) {
        boolean changed = false;

        RecipeHolder<CombinerRecipe> recipeHolder = blockEntity.quickCheck.getRecipeFor(new CombinerRecipeInput(Collections.singletonList(blockEntity.getItem(0))), level).orElse(null);
        int i = blockEntity.getMaxStackSize();
        boolean isCharging = false;

        if (blockEntity.canProcess(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
            changed = true;
            ++blockEntity.processingProgress;
            isCharging = true;
            if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                blockEntity.processingProgress = 0;
                blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                if (blockEntity.process(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                    blockEntity.setRecipeUsed(recipeHolder);
                }
                isCharging = false;
                blockEntity.combining = true;
                blockEntity.combiningDuration = 15;
            }
        } else {
            blockEntity.processingProgress = 0;
        }

        if (blockEntity.processingProgress > 0) {
            blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingProgress);
        }

        if (blockEntity.combiningDuration-- <= 0) {
            blockEntity.combining = false;
        }

        if (state.getValue(CombinerBlock.CHARGING) != isCharging) {
            changed = true;
            state = state.setValue(CombinerBlock.CHARGING, isCharging);
            level.setBlock(pos, state, 1 | 2);
        }

        if (state.getValue(CombinerBlock.combining) != blockEntity.combining) {
            changed = true;
            state = state.setValue(CombinerBlock.combining, blockEntity.combining);
            level.setBlock(pos, state, 1 | 2);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private boolean canProcess(RegistryAccess registryAccess, @Nullable RecipeHolder<CombinerRecipe> recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        ItemStack input = stacks.get(0);
        if (!input.isEmpty() && recipeHolder != null) {
            ItemStack result = recipeHolder.value().assemble(new CombinerRecipeInput(Collections.singletonList(this.getItem(0))), registryAccess);
            if (result.isEmpty()) {
                return false;
            } else {
                ItemStack inResultSlot = stacks.get(9);
                if (inResultSlot.isEmpty()) {
                    if (ItemStack.isSameItem(input, result)) {
                        return !input.has(DataComponents.MAX_DAMAGE) || input.getDamageValue() > 0;
                    } else {
                        return true;
                    }
                } else if (!ItemStack.isSameItem(inResultSlot, result)) {
                    return false;
                } else if (inResultSlot.getCount() + result.getCount() <= maxStackSize && inResultSlot.getCount() + result.getCount() <= inResultSlot.getMaxStackSize()) {
                    return true;
                } else {
                    return inResultSlot.getCount() + result.getCount() <= result.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean process(RegistryAccess registryAccess, @Nullable RecipeHolder<CombinerRecipe> recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        if (recipeHolder != null && this.canProcess(registryAccess, recipeHolder, stacks, maxStackSize)) {
            ItemStack input = stacks.get(0);
            ItemStack result = recipeHolder.value().assemble(new CombinerRecipeInput(Collections.singletonList(this.getItem(0))), registryAccess);
            ItemStack output = stacks.get(9);
            if (output.isEmpty()) {
                if (ItemStack.isSameItem(input, result) && input.has(DataComponents.MAX_DAMAGE) && input.getDamageValue() > 0) {
                    ItemStack copy = input.copy();
                    copy.setDamageValue(0);
                    stacks.set(9, copy);
                } else {
                    stacks.set(9, result.copy());
                }
            } else if (output.is(result.getItem())) {
                output.grow(result.getCount());
            }
            input.shrink(1);
            return true;
        } else {
            return false;
        }
    }


    private static int getTotalProcessingTime(Level level, CombinerBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(new CombinerRecipeInput(Collections.singletonList(blockEntity.getItem(0))), level).map(recipeHolder -> recipeHolder.value().getProcessingTime()).orElse(200);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void resetProgress() {
        processingProgress = 0;
    }

    private void craftItem() {
        Optional<RecipeHolder<CombinerRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        consumeIngredients();

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }


    private boolean hasRecipe() {
        Optional<RecipeHolder<CombinerRecipe>> recipe = getCurrentRecipe();

        if(recipe.isEmpty())
            return false;

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());
        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }


    private Optional<RecipeHolder<CombinerRecipe>> getCurrentRecipe() {

        NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

        for(int i = 0; i < itemHandler.getSlots(); i++)
            items.add(i, this.itemHandler.getStackInSlot(i));
        return this.level.getRecipeManager().getRecipeFor(DARecipeTypes.COMBINING.get(), new CombinerRecipeInput(items), level);
    }


    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private void consumeIngredients(){
        this.itemHandler.extractItem(FIRST_SLOT, 1, false);
        this.itemHandler.extractItem(SECOND_SLOT, 1, false);
        this.itemHandler.extractItem(THIRD_SLOT, 1, false);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return new int[]{OUTPUT_SLOT};
        } else {
            return new int[]{FIRST_SLOT, SECOND_SLOT, THIRD_SLOT};
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return direction != Direction.DOWN;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.processingTotalTime = getTotalProcessingTime(this.level, this);
            this.processingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {
        if (recipeHolder != null) {
            ResourceLocation location = recipeHolder.id();
            this.recipesUsed.addTo(location, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);
        for (RecipeHolder<?> recipeholder : list) {
            if (recipeholder != null) {
                player.triggerRecipeCrafted(recipeholder, this.items);
            }
        }
        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                list.add(recipeHolder);
                createExperience(level, popVec, entry.getIntValue(), ((CombinerRecipe) recipeHolder.value()).getExperience());
            });
        }
        return list;
    }

    private static void createExperience(ServerLevel level, Vec3 popVec, int recipeIndex, float experience) {
        int i = Mth.floor((float) recipeIndex * experience);
        float f = Mth.frac((float) recipeIndex * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrb.award(level, popVec, i);
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {
        for (ItemStack itemstack : this.items) {
            stackedContents.accountStack(itemstack);
        }
    }
}