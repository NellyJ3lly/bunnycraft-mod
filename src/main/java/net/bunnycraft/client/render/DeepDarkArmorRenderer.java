package net.bunnycraft.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;



public class DeepDarkArmorRenderer implements ArmorRenderer {
    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, ItemStack itemStack, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int i, BipedEntityModel<LivingEntity> bipedEntityModel) {

    }
}
