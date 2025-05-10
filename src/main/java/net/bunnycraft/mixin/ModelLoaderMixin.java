package net.bunnycraft.mixin;


import net.bunnycraft.Bunnycraft;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "steel_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "netherite_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "diamond_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "golden_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "iron_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "copper_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "stone_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "wooden_spear_item")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "empty_spear_slot_gui")));
    }
}