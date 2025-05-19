package net.bunnycraft.util;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModTools;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

//registers model predicates for every spear, used to prevent the spear from being backwards when charging it up

public class ModModelPredicates {
    public static void registerModelPredicates() {
        for(int i = 0; ModTools.spearList.get(i) != null; i++) {

            ModelPredicateProviderRegistry.register
                    (ModTools.spearList.get(i),
                            Identifier.of(Bunnycraft.MOD_ID, "charging"),
                            (stack, world, entity, seed)

                                    //checks if the player is charging up the spear
                                    -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                    );

        }
    }
}
