package io.github.razordevs.deep_aether.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.client.model.BabyEotsModel;
import io.github.razordevs.deep_aether.client.renderer.DAModelLayers;
import io.github.razordevs.deep_aether.entity.BabyEots;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
    public void render(BabyEots eots, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();

        this.model.attackTime = this.getAttackAnim(eots, partialTicks);
        float f = Mth.rotLerp(partialTicks, eots.yBodyRotO, eots.yBodyRot);
        float f1 = Mth.rotLerp(partialTicks, eots.yHeadRotO, eots.yHeadRot);
        float f2 = f1 - f;

        float f6 = Mth.lerp(partialTicks, eots.xRotO, eots.getXRot());
        if (isEntityUpsideDown(eots)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }

        f2 = Mth.wrapDegrees(f2);

        float f8 = eots.getScale();
        matrixStack.scale(f8, f8, f8);
        float f9 = this.getBob(eots, partialTicks);
        this.setupRotations(eots, matrixStack, f9, f, partialTicks, f8);
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(eots, matrixStack, partialTicks);
        matrixStack.translate(0.0F, -1.501F, 0.0F);
        float f4 = 0.0F;
        float f5 = 0.0F;
        if (eots.isAlive()) {
            f4 = eots.walkAnimation.speed(partialTicks);
            f5 = eots.walkAnimation.position(partialTicks);

            if (f4 > 1.0F) {
                f4 = 1.0F;
            }
        }

        this.model.prepareMobModel(eots, f5, f4, partialTicks);
        this.model.setupAnim(eots, f5, f4, f9, f2, f6);
        RenderType rendertype = this.model.renderType(this.getTextureLocation(eots));
        VertexConsumer vertexconsumer = buffer.getBuffer(rendertype);
        int i = getOverlayCoords(eots, this.getWhiteOverlayProgress(eots, partialTicks));

        if(eots.isWrappedAroundNeck()){
            this.model.head.visible = false;
            for (ModelPart part : this.model.body)
                part.visible = false;
        }else {
            this.model.head.visible = true;
            for (ModelPart part : this.model.body)
                part.visible = true;
        }

        this.model.head.render(matrixStack, vertexconsumer, packedLight, i, eots.getFromColor(0));
        this.model.body[0].render(matrixStack, vertexconsumer, packedLight, i, eots.getFromColor(1));
        this.model.body[1].render(matrixStack, vertexconsumer, packedLight, i, eots.getFromColor(2));
        this.model.body[2].render(matrixStack, vertexconsumer, packedLight, i, eots.getFromColor(3));
        this.model.body[3].render(matrixStack, vertexconsumer, packedLight, i, eots.getFromColor(4));

        matrixStack.popPose();

        if (this.shouldShowName(eots) && eots.getDisplayName() != null && !eots.isWrappedAroundNeck()) {
            this.renderNameTag(eots, eots.getDisplayName(), matrixStack, buffer, packedLight, partialTicks);
        }
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
