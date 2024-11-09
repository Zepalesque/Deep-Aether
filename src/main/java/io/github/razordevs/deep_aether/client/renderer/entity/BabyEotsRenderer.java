package io.github.razordevs.deep_aether.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.client.model.BabyEotsModel;
import io.github.razordevs.deep_aether.client.renderer.DAModelLayers;
import io.github.razordevs.deep_aether.entity.BabyEots;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BabyEotsRenderer extends MobRenderer<BabyEots, BabyEotsModel> {
    public BabyEotsRenderer(EntityRendererProvider.Context context) {
        super(context, new BabyEotsModel(context.bakeLayer(DAModelLayers.BABY_EOTS)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(BabyEots pEntity) {
        return ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "textures/entity/baby_eots.png");
    }

    @Override
    protected void setupRotations(BabyEots pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, float scale) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks, scale);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pAgeInTicks, pEntityLiving.xRotO, pEntityLiving.getXRot())));
    }

    @Override
    protected float getBob(BabyEots eots, float partialTicks) {
        return partialTicks;
    }
}
