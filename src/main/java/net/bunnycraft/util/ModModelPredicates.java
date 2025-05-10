package net.bunnycraft.util;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModTools;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

//registers model predicates for every spear, used to prevent the spear from being backwards when charging it up

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register
                (ModTools.STEEL_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.NETHERITE_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.DIAMOND_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.GOLDEN_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.IRON_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.COPPER_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.STONE_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
        ModelPredicateProviderRegistry.register
                (ModTools.WOODEN_SPEAR,
                        Identifier.of(Bunnycraft.MOD_ID, "charging"),
                        (stack, world, entity, seed)

                                //checks if the player is charging up the spear
                                -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f

                );
    }
}
