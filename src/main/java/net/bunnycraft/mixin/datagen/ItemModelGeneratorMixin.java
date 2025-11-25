package net.bunnycraft.mixin.datagen;

import net.bunnycraft.item.armor.ModArmorMaterials;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

import static net.minecraft.registry.tag.ItemTags.TRIM_MATERIALS;

@Mixin(ItemModelGenerator.class)
public class ItemModelGeneratorMixin {
    @Unique
    ItemModelGenerator generator = (ItemModelGenerator) (Object) this;


    @Inject(
            method = "registerArmor(Lnet/minecraft/item/ArmorItem;)V",
            at = @At("HEAD"),
            cancellable = true)
    public void registerDivingSuit(ArmorItem armor, CallbackInfo ci) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");

        // need to figure out how to add trims later

        // the diving helmet doesn't have leather on it so it doesn't need an overlay
        if (armor.getMaterial().matches(ModArmorMaterials.DIVING_MATERIAL)
                && armor.getSlotType() != EquipmentSlot.HEAD) {
            ci.cancel();

            Models.GENERATED_TWO_LAYERS
                    .upload(identifier, TextureMap.layered(identifier2, identifier3), generator.writer, (id, textures) -> generator.createArmorJson(id, textures, armor.getMaterial()));
        }
    }
}
