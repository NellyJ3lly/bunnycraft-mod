package net.bunnycraft.util;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.component.ModComponents;
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

        ModelPredicateProviderRegistry.register(ModTools.ECHOLOCATOR,Identifier.of(Bunnycraft.MOD_ID,"echo_fuel"),
                (stack,world,entity,seed) -> {
                    float Fuel = stack.get(ModComponents.ECHO_FUEL);

                    // for some reason it just doesn't want to use the main model so I have to add an override for the main model
                    if (Fuel == 0) {
                        return 0.0F;
                    } else {
                        return 0.16F;
                    }
                });
    }
}
