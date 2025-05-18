package net.bunnycraft.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }



    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool pancakerabbittexturepool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PANCAKE_RABBIT_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PANCAKE_RABBIT_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(ModItems.STEEL_INGOT, Models.GENERATED);
        gen.register(ModItems.ROSE_GOLD_INGOT, Models.GENERATED);
        gen.register(ModItems.PANCAKE_RABBIT, Models.GENERATED);

        gen.register(ModTools.COPPER_PICKAXE, Models.HANDHELD);
        gen.register(ModTools.COPPER_SWORD, Models.HANDHELD);
        gen.register(ModTools.COPPER_AXE, Models.HANDHELD);
        gen.register(ModTools.COPPER_SHOVEL, Models.HANDHELD);
        gen.register(ModTools.COPPER_HOE, Models.HANDHELD);

        gen.register(ModTools.STEEL_PICKAXE, Models.HANDHELD);
        gen.register(ModTools.STEEL_AXE, Models.HANDHELD);
        gen.register(ModTools.STEEL_HOE, Models.HANDHELD);
        gen.register(ModTools.STEEL_SWORD, Models.HANDHELD);
        gen.register(ModTools.STEEL_SHOVEL, Models.HANDHELD);

        gen.register(ModTools.ROSE_GOLD_PICKAXE, Models.HANDHELD);
        gen.register(ModTools.ROSE_GOLD_AXE, Models.HANDHELD);
        gen.register(ModTools.ROSE_GOLD_HOE, Models.HANDHELD);
        gen.register(ModTools.ROSE_GOLD_SWORD, Models.HANDHELD);
        gen.register(ModTools.ROSE_GOLD_SHOVEL, Models.HANDHELD);

        for(int i = 0; ModTools.getSpear(i) != null; i++) {
            registerSpearModels(gen, ModTools.getSpear(i));
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
