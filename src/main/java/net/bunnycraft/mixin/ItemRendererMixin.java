package net.bunnycraft.mixin;


import com.llamalad7.mixinextras.sugar.Local;
import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    //@Shadow
    //@Final
    //private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )

    //specifys to render a different model if the item is in gui (inventory or something) on the ground or fixed (i think thats item frame)
    //otherwise it will render the model
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED) {

            if (stack.getItem() == ModTools.STEEL_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "steel_spear_item")));
            } else if (stack.getItem() == ModTools.NETHERITE_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "netherite_spear_item")));
            } else if (stack.getItem() == ModTools.DIAMOND_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "diamond_spear_item")));
            } else if (stack.getItem() == ModTools.GOLDEN_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "golden_spear_item")));
            } else if (stack.getItem() == ModTools.IRON_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "iron_spear_item")));
            } else if (stack.getItem() == ModTools.COPPER_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "copper_spear_item")));
            } else if (stack.getItem() == ModTools.STONE_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "stone_spear_item")));
            } else if (stack.getItem() == ModTools.WOODEN_SPEAR) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "wooden_spear_item")));
            } else if (stack.getItem() == ModItems.EMPTY_SPEAR_SLOT) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "empty_spear_slot_gui")));
            }
        }

        return bakedModel;
    }
}