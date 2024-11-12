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

        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(2, 13).addBox(-5.0F, -2.0F, -5.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 9).addBox(-4.0F, -2.0F, 4.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 1).addBox(-5.0F, -2.0F, -1.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(2, 5).addBox(-4.0F, -2.0F, 0.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, 0.0F, 1.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }
}
