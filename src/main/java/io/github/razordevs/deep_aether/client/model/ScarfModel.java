package io.github.razordevs.deep_aether.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ScarfModel extends HumanoidModel<LivingEntity> {

    public ScarfModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        CubeDeformation cube = new CubeDeformation(0.6F);
        MeshDefinition meshDefinition = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-6.0F, -2.0F, -6.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-4.0F, -2.0F, 4.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        head.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        head.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(0, 4).addBox(-5.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, 0.0F, 1.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }
}
