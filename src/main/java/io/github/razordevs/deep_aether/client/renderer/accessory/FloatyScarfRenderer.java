package io.github.razordevs.deep_aether.client.renderer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.client.model.ScarfModel;
import io.github.razordevs.deep_aether.client.renderer.DAModelLayers;
import io.github.razordevs.deep_aether.entity.living.BabyEots;
import io.github.razordevs.deep_aether.item.gear.other.FloatyScarfItem;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FloatyScarfRenderer implements AccessoryRenderer {
    private final ScarfModel scarfModel;

    public FloatyScarfRenderer() {
        this.scarfModel = new ScarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(DAModelLayers.SCARF));
    }

    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<M> entityModel, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        BabyEots eots = (BabyEots) FloatyScarfItem.getEOTS(stack, reference.entity().level());

        if(eots == null || !eots.isWrappedAroundNeck()) return;

        AccessoryRenderer.followBodyRotations(reference.entity(), this.scarfModel);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "textures/models/accessory/pendant/scarf.png")));

        this.scarfModel.head.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, eots.getFromColor(0));
        this.scarfModel.body[0].render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, eots.getFromColor(1));
        this.scarfModel.body[1].render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, eots.getFromColor(2));
        this.scarfModel.body[2].render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, eots.getFromColor(3));
        this.scarfModel.body[3].render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, eots.getFromColor(4));
    }
}
