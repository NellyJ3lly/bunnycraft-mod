package net.bunnycraft.datagen.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ibm.icu.text.Normalizer2;
import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.block.SculkBerryBushBlock;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.ModArmors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateInsideBlocks(BlockStateModelGenerator blockStateModelGenerator, Block block) {

    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool pancakerabbittexturepool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PANCAKE_RABBIT_BLOCK);
        BlockStateModelGenerator.BlockTexturePool sculkwoodplanktexturepool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SCULK_WOOD_PLANKS);
        BlockStateModelGenerator.BlockTexturePool echobricktexturepool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ECHO_BRICK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PANCAKE_RABBIT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ROSE_GOLD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STEEL_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BUDDING_ECHO);

        blockStateModelGenerator.registerAmethyst(ModBlocks.ECHO_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_ECHO_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_ECHO_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_ECHO_BUD);

        blockStateModelGenerator.registerLog(ModBlocks.SCULK_WOOD_LOG).log(ModBlocks.SCULK_WOOD_LOG).wood(ModBlocks.SCULK_WOOD_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_SCULK_WOOD_LOG).log(ModBlocks.STRIPPED_SCULK_WOOD_LOG).wood(ModBlocks.STRIPPED_SCULK_WOOD_WOOD);
        blockStateModelGenerator.registerDoor(ModBlocks.SCULK_WOOD_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.SCULK_WOOD_TRAPDOOR);

        sculkwoodplanktexturepool.stairs(ModBlocks.SCULK_WOOD_STAIRS);
        sculkwoodplanktexturepool.slab(ModBlocks.SCULK_WOOD_SLAB);
        sculkwoodplanktexturepool.button(ModBlocks.SCULK_WOOD_BUTTON);
        sculkwoodplanktexturepool.pressurePlate(ModBlocks.SCULK_WOOD_PRESSURE_PLATE);
        sculkwoodplanktexturepool.fence(ModBlocks.SCULK_WOOD_FENCE);
        sculkwoodplanktexturepool.fenceGate(ModBlocks.SCULK_WOOD_FENCE_GATE);

        echobricktexturepool.stairs(ModBlocks.ECHO_BRICK_STAIRS);
        echobricktexturepool.slab(ModBlocks.ECHO_BRICK_SLAB);
        echobricktexturepool.wall(ModBlocks.ECHO_BRICK_WALL);

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.SCULK_WOOD_SAPLING,BlockStateModelGenerator.TintType.NOT_TINTED);
    }


    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(ModItems.STEEL_INGOT, Models.GENERATED);
        gen.register(ModItems.BREEZE_INGOT, Models.GENERATED);
        gen.register(ModItems.ROSE_GOLD_INGOT, Models.GENERATED);
        gen.register(ModItems.PANCAKE_RABBIT, Models.GENERATED);
        gen.register(ModItems.STEEL_UPGRADE_TEMPLATE, Models.GENERATED);

        gen.register(Item.fromBlock(ModBlocks.ECHO_CLUSTER), Models.GENERATED);
        gen.register(Item.fromBlock(ModBlocks.LARGE_ECHO_BUD), Models.GENERATED);
        gen.register(Item.fromBlock(ModBlocks.MEDIUM_ECHO_BUD), Models.GENERATED);
        gen.register(Item.fromBlock(ModBlocks.SMALL_ECHO_BUD), Models.GENERATED);
        gen.register(Item.fromBlock(ModBlocks.SCULK_WOOD_SAPLING), Models.GENERATED);

        gen.register(ModItems.BUNNYCENT, Models.GENERATED);
        gen.register(ModItems.COPPER_BUNNYCOIN, Models.GENERATED);
        gen.register(ModItems.GOLD_BUNNYCOIN, Models.GENERATED);
        gen.register(ModItems.DIAMOND_BUNNYCOIN, Models.GENERATED);
        gen.register(ModItems.PIPIS, Models.GENERATED);
        gen.register(ModItems.MS_PIPIS, Models.GENERATED);
        gen.register(ModItems.VAULT_REWINDER,Models.GENERATED);

        gen.register(ModItems.MOLTEN_ROSE_GOLD, Models.GENERATED);
        gen.register(ModItems.MOLTEN_STEEL, Models.GENERATED);
        gen.register(ModItems.MOLTEN_NETHERITE, Models.GENERATED);
        gen.register(ModItems.MOLD, Models.GENERATED);

//        gen.register(ModItems.SCULK_BERRIES, Models.GENERATED);

        gen.register(ModItems.AMETHYST_BOOK,Models.GENERATED);
        gen.register(ModItems.ENCHANTED_AMETHYST_BOOK,Models.GENERATED);
        gen.register(ModItems.ECHO_INGOT,Models.GENERATED);
        gen.register(ModItems.AMETHYST_UPGRADE_TEMPLATE,Models.GENERATED);

        gen.register(ModTools.STEEL_SHEARS,Models.GENERATED);

        gen.registerArmor(((ArmorItem) ModArmors.STEEL_HELMET));
        gen.registerArmor(((ArmorItem) ModArmors.STEEL_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.STEEL_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.STEEL_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.COPPER_HELMET));
        gen.registerArmor(((ArmorItem) ModArmors.COPPER_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.COPPER_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.COPPER_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.GUARDIAN_HELMET));
        gen.registerArmor(((ArmorItem) ModArmors.GUARDIAN_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.GUARDIAN_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.GUARDIAN_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.DIVING_HELMET));
        gen.registerArmor(((ArmorItem) ModArmors.DIVING_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.DIVING_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.DIVING_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.ARMADILLO_HELMET));
        gen.registerArmor(((ArmorItem) ModArmors.ARMADILLO_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.ARMADILLO_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.ARMADILLO_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.ROSE_GOLD_HELMET));
        gen.registerArmor(((ArmorItem) ModArmors.ROSE_GOLD_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.ROSE_GOLD_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.ROSE_GOLD_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.TURTLE_CHESTPLATE));
        gen.registerArmor(((ArmorItem) ModArmors.TURTLE_LEGGINGS));
        gen.registerArmor(((ArmorItem) ModArmors.TURTLE_BOOTS));

        gen.registerArmor(((ArmorItem) ModArmors.DEALMAKER));
        gen.register(ModTools.CLIMBING_CLAW, Models.GENERATED);

        gen.register(ModItems.TRUE, Models.GENERATED);
        gen.register(ModItems.POINT_LIGHT_MUSIC_DISC, Models.GENERATED);

        //add pickaxes
        for(int i = 0; ModTools.pickaxeList.get(i) != null; i++) {
            gen.register(ModTools.pickaxeList.get(i), Models.HANDHELD);
        }

        //add swords
        for(int i = 0; ModTools.swordList.get(i) != null; i++) {
            gen.register(ModTools.swordList.get(i), Models.HANDHELD);
        }

        //add spears
        for(int i = 0; ModTools.spearList.get(i) != null; i++) {
            if (ModTools.spearList.get(i).getTranslationKey().substring(16).equals("rose_gold_spear")) {
                Bunnycraft.LOGGER.info("skipping model datagen for rose_gold_spear");
            } else {
                registerSpearModels(gen, ModTools.spearList.get(i));
            }
        }

        //add axes
        for(int i = 0; ModTools.axeList.get(i) != null; i++) {
            gen.register(ModTools.axeList.get(i), Models.HANDHELD);
        }

        //add shovels
        for(int i = 0; ModTools.shovelList.get(i) != null; i++) {
            gen.register(ModTools.shovelList.get(i), Models.HANDHELD);
        }

        //add hoes
        for(int i = 0; ModTools.hoeList.get(i) != null; i++) {
            gen.register(ModTools.hoeList.get(i), Models.HANDHELD);
        }
    }

    //------------------------------------------------------------------------------------------------- Don't use these methods for anything but generating spears

    //the template for new spears
    private static final Identifier SPEAR_TEMPLATE = Identifier.of(Bunnycraft.MOD_ID, "item/template_spear");
    private static final Identifier SPEAR_TEMPLATE_THROWING = Identifier.of(Bunnycraft.MOD_ID, "item/template_spear_throwing");

    //helper method that handles all the spear json building
    private void registerSpearModels(ItemModelGenerator modelGenerator, Item spear) {

        //gets the name of the spear
        String name = Registries.ITEM.getId(spear).getPath();


        //gets the json paths
        Identifier handHeldId = Identifier.of(Bunnycraft.MOD_ID, "item/" + name);
        Identifier inventoryId = Identifier.of(Bunnycraft.MOD_ID, "item/" + name + "_item");
        Identifier throwingId = Identifier.of(Bunnycraft.MOD_ID, "item/" + name + "_throwing");


        //generate the handheld model
        JsonObject handHeld = createCustomModelJson(name, SPEAR_TEMPLATE);
        addPredicateOverride(handHeld, Identifier.of(Bunnycraft.MOD_ID, "charging"), throwingId);
        modelGenerator.writer.accept(handHeldId, () -> handHeld);

        //generates the inventory model
        JsonObject inventory = createCustomModelJson(name);
        modelGenerator.writer.accept(inventoryId, () -> inventory);

        //generates the throwing model
        JsonObject throwing = createCustomModelJson(name, SPEAR_TEMPLATE_THROWING);
        modelGenerator.writer.accept(throwingId, () -> throwing);

    }

    //helper method for registering the custom model properties like textures
    private JsonObject createCustomModelJson(String texturePath, Identifier template) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", template.toString());

        JsonObject textures = new JsonObject();
        textures.addProperty("0", Bunnycraft.MOD_ID + ":item/model_" + texturePath);
        textures.addProperty("particle", Bunnycraft.MOD_ID + ":item/" + texturePath);
        model.add("textures", textures);
        return model;
    }

    //overflow helper method for registering the generated json model
    private JsonObject createCustomModelJson(String texturePath) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", Bunnycraft.MOD_ID + ":item/" + texturePath);
        model.add("textures", textures);
        return model;
    }


    //adds a predicate
    private void addPredicateOverride(JsonObject modelJson, Identifier predicateProperty, Identifier targetModelId) {
        JsonArray overridesArray;
        if (modelJson.has("overrides") && modelJson.get("overrides").isJsonArray()) {
            overridesArray = modelJson.getAsJsonArray("overrides");
        } else {
            overridesArray = new JsonArray();
        }

        JsonObject predicateDetails = new JsonObject();
        predicateDetails.addProperty(predicateProperty.toString(), 1f);

        JsonObject overrideEntry = new JsonObject();
        overrideEntry.add("predicate", predicateDetails);
        overrideEntry.addProperty("model", targetModelId.toString());

        overridesArray.add(overrideEntry);
        modelJson.add("overrides", overridesArray);
    }
}
