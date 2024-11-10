package io.github.razordevs.deep_aether.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.razordevs.deep_aether.entity.BabyEots;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BabyEotsModel extends EntityModel<BabyEots> {
	public final ModelPart[] body = new ModelPart[4];
	public final ModelPart head;
	private final ModelPart[] bodyRot = new ModelPart[4];

	public BabyEotsModel(ModelPart root) {
		this.body[0] = root.getChild("body_0");
		this.body[1] = root.getChild("body_1");
		this.body[2] = root.getChild("body_2");
		this.body[3] = root.getChild("body_3");
		this.bodyRot[0] = this.body[0].getChild("body_rot_0");
		this.bodyRot[1] = this.body[1].getChild("body_rot_1");
		this.bodyRot[2] = this.body[2].getChild("body_rot_2");
		this.bodyRot[3] = this.body[3].getChild("body_rot_3");
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body_0 = partdefinition.addOrReplaceChild("body_0", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		body_0.addOrReplaceChild("body_rot_0", CubeListBuilder.create().texOffs(12, 12).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition body_1 = partdefinition.addOrReplaceChild("body_1", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		body_1.addOrReplaceChild("body_rot_1", CubeListBuilder.create().texOffs(12, 4).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

		PartDefinition body_2 = partdefinition.addOrReplaceChild("body_2", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		body_2.addOrReplaceChild("body_rot_2", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition body_3 = partdefinition.addOrReplaceChild("body_3", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		body_3.addOrReplaceChild("body_rot_3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 21.0F));

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(12, 0).addBox(-2.0F, 0.5F, -3.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 17).addBox(0.0F, -2.5F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(BabyEots entity, float limbSwing, float limbSwingAmount, float partialTicks, float netHeadYaw, float headPitch) {
		for (int i = 0; i < body.length; i++) {

			//Roll
			float currentTick = (entity.tickCount + i * 5) % 400;
			if (currentTick < 20) {
				bodyRot[i].zRot = Mth.lerp(partialTicks,0.0F, 0.3141592653589793F) + currentTick * 0.3141592653589793F;
			} else {
				bodyRot[i].zRot = Mth.lerp(0.02F, 0, bodyRot[i].zRot);
			}

			//Rotation
			body[i].xRot = Mth.lerp(0.8F, 0, headPitch * (float) (Math.PI / 180.0) * i * 0.15F - body[i].xRot);
			body[i].yRot = Mth.lerp(0.9F, 0, netHeadYaw * (float) (Math.PI / 180.0) * i * 0.3F - body[i].yRot * 0.7F);

		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
	}
}