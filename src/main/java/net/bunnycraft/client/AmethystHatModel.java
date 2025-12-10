package net.bunnycraft.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class AmethystHatModel extends BipedEntityModel<LivingEntity> {
    public AmethystHatModel(ModelPart root) {
        super(root);
    }

    public static ModelData getModelData(Dilation dilation, float pivotOffsetY) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation), ModelTransform.pivot(0.0F, 0.0F + pivotOffsetY, 0.0F));

        return modelData;
    }
}
