package net.bunnycraft.item.custom;

import net.bunnycraft.Bunnycraft;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class ModSmithingTemplateItem extends SmithingTemplateItem {
    public ModSmithingTemplateItem(Text appliesToText, Text ingredientsText, Text titleText, Text baseSlotDescriptionText, Text additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures, FeatureFlag... requiredFeatures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures, requiredFeatures);
    }

    // Will use this for steel and amethyst upgrade templates but for now don't worry about it lol
}

