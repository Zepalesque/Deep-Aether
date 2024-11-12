package io.github.razordevs.deep_aether.entity.projectile;

import io.github.razordevs.deep_aether.init.DAItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class VenomiteBubble extends ThrowableProjectile {
    private int ticksInAir = 0;

    public VenomiteBubble(EntityType<? extends VenomiteBubble> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround())
            ++this.ticksInAir;

        if (this.ticksInAir > 300 && !this.level().isClientSide())
            this.discard();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide()) {
            this.explode();
            this.level().broadcastEntityEvent(this, (byte) 70);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.explode();
        }
    }

    private void explode() {
        level().explode(this, this.getX(),this.getY(),this.getZ(),1, Level.ExplosionInteraction.NONE);
        level().addFreshEntity(new ItemEntity(level(), this.getX(),this.getY(),this.getZ(), new ItemStack(DAItems.BIO_CRYSTAL.asItem())));
    }

    @Override
    protected double getDefaultGravity() {
        return 0.07F;
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TicksInAir", this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("TicksInAir")) {
            this.ticksInAir = tag.getInt("TicksInAir");
        }
    }
}

