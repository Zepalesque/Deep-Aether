package io.github.razordevs.deep_aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.entity.MultiModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.DeepAetherConfig;
import io.github.razordevs.deep_aether.client.model.ClassicEOTSSegmentModel;
import io.github.razordevs.deep_aether.client.model.EOTSSegmentModel;
import io.github.razordevs.deep_aether.client.renderer.DAModelLayers;
import io.github.razordevs.deep_aether.entity.living.boss.eots.EOTSSegment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EOTSSegmentRenderer extends MultiModelRenderer<EOTSSegment, EntityModel<EOTSSegment>, EOTSSegmentModel, ClassicEOTSSegmentModel> {
	private static final ResourceLocation EOTS_SEGMENT_LOCATION = ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "textures/entity/eots/eots_segment.png");
	private static final ResourceLocation EOTS_SEGMENT_CONTROLLING_LOCATION = ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "textures/entity/eots/eots_segment_controlling.png");

	private static final ResourceLocation EOTS_SEGMENT_LOCATION_CLASSIC = ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "textures/entity/eots/eots_segment_classic.png");
	private static final ResourceLocation EOTS_SEGMENT_CONTROLLING_LOCATION_CLASSIC = ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "textures/entity/eots/eots_segment_controlling_classic.png");

	private final EOTSSegmentModel defaultModel;
	private final ClassicEOTSSegmentModel oldModel;

	public EOTSSegmentRenderer(EntityRendererProvider.Context renderer) {
		super(renderer, new EOTSSegmentModel(renderer.bakeLayer(DAModelLayers.EOTS_SEGMENT)), 0F);
		this.defaultModel = new EOTSSegmentModel(renderer.bakeLayer(DAModelLayers.EOTS_SEGMENT));
		this.oldModel = new ClassicEOTSSegmentModel(renderer.bakeLayer(DAModelLayers.EOTS_SEGMENT_CLASSIC));
	}

	@Override
	public ResourceLocation getTextureLocation(EOTSSegment segment) {
		if(DeepAetherConfig.CLIENT.legacy_models.get()){
			return segment.isControllingSegment() ? EOTS_SEGMENT_CONTROLLING_LOCATION_CLASSIC : EOTS_SEGMENT_LOCATION_CLASSIC;
		} else {
			return segment.isControllingSegment() ? EOTS_SEGMENT_CONTROLLING_LOCATION : EOTS_SEGMENT_LOCATION;
		}
	}

	@Override
	public ResourceLocation getDefaultTexture() {
		return EOTS_SEGMENT_CONTROLLING_LOCATION;
	}

	@Override
	public ResourceLocation getOldTexture() {
		return EOTS_SEGMENT_CONTROLLING_LOCATION_CLASSIC;
	}

	@Override
	public EOTSSegmentModel getDefaultModel() {
		return this.defaultModel;
	}

	@Override
	public ClassicEOTSSegmentModel getOldModel() {
		return this.oldModel;
	}

	@Override
	protected float getFlipDegrees(EOTSSegment eotsSegment) {
		return 0F;
	}

	@Override
	public void render(EOTSSegment eots, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		if(eots.isDeadOrDying()) {
			pPoseStack.scale(eots.getScale() - (float) eots.deathTime/20 , eots.getScale() - (float) eots.deathTime/20, eots.getScale() - (float) eots.deathTime/20);
		}

		pPoseStack.scale(1.2f, 1.2f, 1.2f);
		super.render(eots, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
	}

	@Override
	protected void scale(EOTSSegment eotsSegment, PoseStack poseStack, float scale) {
		super.scale(eotsSegment, poseStack, scale);
	}

	@Override
	protected float getBob(EOTSSegment pLivingBase, float pPartialTick) {
		return pPartialTick;
	}

	@Override
	protected void setupRotations(EOTSSegment pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, float scale) {
		super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks, scale);
		pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pAgeInTicks, pEntityLiving.xRotO, pEntityLiving.getXRot())));
	}
}
