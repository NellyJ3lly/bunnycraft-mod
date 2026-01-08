package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.block.SculkBerryBushBlock;
import net.bunnycraft.item.custom.AmethystBookItem;
import net.bunnycraft.item.custom.BunnycoinItem;
import net.bunnycraft.item.custom.EnchantedAmethystBookItem;
import net.bunnycraft.item.custom.ModSmithingTemplateItem;
import net.bunnycraft.sound.ModSounds;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class        ModItems {
    public static final Identifier BASE_PLAYER_ENTITY_INTERACTION_RANGE_ID = Identifier.of(Bunnycraft.MOD_ID, "base_entity_interaction_range");

    //registering as an ingot adds it to a public list currently used only by cauldron alloyer
    public static  final Item ROSE_GOLD_INGOT = registerIngot("rose_gold_ingot", new Item(new Item.Settings()));
    public static  final Item STEEL_INGOT = registerIngot("steel_ingot", new Item(new Item.Settings()));
    public static  final Item BREEZE_INGOT = registerIngot("breeze_ingot", new Item(new Item.Settings()));
    public static  final Item ECHO_INGOT = registerIngot("echo_ingot", new Item(new Item.Settings()));

    public static  final Item AMETHYST_BOOK = registerItem("amethyst_book", new AmethystBookItem(new Item.Settings()));
    public static  final Item ENCHANTED_AMETHYST_BOOK = registerItem("enchanted_amethyst_book", new EnchantedAmethystBookItem(new Item.Settings().maxCount(1).component(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true).component(DataComponentTypes.RARITY, Rarity.RARE)));

    public static  final Item VAULT_REWINDER = registerItem("vault_rewinder", new Item(new Item.Settings()));
    public static  final Item STEEL_UPGRADE_TEMPLATE = registerItem("steel_upgrade_template", new Item(new Item.Settings()));

    public static  final Item AMETHYST_UPGRADE_TEMPLATE = registerItem("amethyst_upgrade_template",new Item(new Item.Settings()));

    public static  final Item TRUE = registerItem("true", new Item(new Item.Settings()));
    public static  final Item POINT_LIGHT_MUSIC_DISC = registerItem("point_light_music_disc", new Item(new Item.Settings().jukeboxPlayable(ModSounds.POINT_LIGHT_KEY).maxCount(1)));

    public static final FoodComponent SCULK_BERRY_FOOD = new FoodComponent.Builder()
            .nutrition(3).saturationModifier(3).snack().build();

    public static  final Item SCULK_BERRIES = registerItem("sculk_berries", new AliasedBlockItem(ModBlocks.SCULK_BERRY_BUSH,new Item.Settings().food(SCULK_BERRY_FOOD)));


    public static  final Item MOLTEN_ROSE_GOLD = registerItem("molten_rose_gold", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    public static  final Item MOLTEN_STEEL = registerItem("molten_steel", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    public static  final Item MOLTEN_NETHERITE = registerItem("molten_netherite", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    public static  final Item MOLTEN_ECHO = registerItem("molten_echo", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));

    public static  final Item PANCAKE_RABBIT = registerItem("pancake_rabbit",SmithingTemplateItem.of(Identifier.of(Bunnycraft.MOD_ID,"rabbit_trim"), FeatureFlags.VANILLA));

    public static  final Item BUNNYCENT = registerItem("bunnycent", new BunnycoinItem(new Item.Settings()));
    public static  final Item COPPER_BUNNYCOIN = registerItem("copper_bunnycoin", new BunnycoinItem(new Item.Settings()));
    public static  final Item GOLD_BUNNYCOIN = registerItem("gold_bunnycoin", new BunnycoinItem(new Item.Settings()));
    public static  final Item DIAMOND_BUNNYCOIN = registerItem("diamond_bunnycoin", new BunnycoinItem(new Item.Settings()));

    public static  final Item PIPIS = registerItem("pipis", new Item(new Item.Settings()));
    public static  final Item MS_PIPIS = registerItem("ms_pipis", new Item(new Item.Settings()));

    public static  final Item MOLD = registerItem("mold", new Item( new Item.Settings()));

    //item that reserves a slot for your spear
    public static final Item EMPTY_SPEAR_SLOT = registerItem("empty_spear_slot", new Item(new Item.Settings().maxCount(1)) {
        //appends a tooltip within an anonymous class
        @Override
        public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {

            //tells what the empty slot item does
            tooltip.add(Text.translatable("tooltip.bunnycraft.empty_slot.instructions"));

            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static Map<Integer, Item> ingotList;
    private static boolean listsRegistered = false;

    private static Item registerIngot(String name, Item item) {

        if (!listsRegistered) {

            ingotList = new HashMap<>();

            listsRegistered = true;

            ingotList.put(0, Items.IRON_INGOT);
            ingotList.put(1, Items.COPPER_INGOT);
            ingotList.put(2, Items.GOLD_INGOT);
            ingotList.put(3, Items.DIAMOND);
            ingotList.put(4, Items.NETHERITE_SCRAP);
            ingotList.put(5, Items.DIRT); // used for a conversion recipe
            ingotList.put(6, Items.ECHO_SHARD);
            //add any other items that should be able to go into the cauldron NOT MOD ITEMS, those should register using this method
            // we should probably change this to just use item tags instead
        }

        assert ingotList != null;
        ingotList.put(ingotList.size(), item);

        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModItems() {
        Bunnycraft.LOGGER.info("Registering Items for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ROSE_GOLD_INGOT);
            entries.add(STEEL_INGOT);
            entries.add(PANCAKE_RABBIT);
            entries.add(COPPER_BUNNYCOIN);
            entries.add(GOLD_BUNNYCOIN);
            entries.add(DIAMOND_BUNNYCOIN);
        });
    }
}
