package net.bunnycraft.client.render;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.armor.AmethystArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AmethystArmorRenderer extends GeoArmorRenderer<AmethystArmorItem> {
    public <I extends AmethystArmorItem> AmethystArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Identifier.of(Bunnycraft.MOD_ID, "armor/amethyst_armor"))); // Using DefaultedItemGeoModel like this puts our 'location' as item/armor/example armor in the assets folders.
    }
}
