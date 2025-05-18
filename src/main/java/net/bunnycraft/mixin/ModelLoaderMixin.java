package net.bunnycraft.mixin;


import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModTools;
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

        for(int i = 0; ModTools.getSpear(i) != null; i++) {
            //loads all spear_items
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, ModTools.getSpearName(i) + "_item")));
        }

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Bunnycraft.MOD_ID, "empty_spear_slot_gui")));
    }
}