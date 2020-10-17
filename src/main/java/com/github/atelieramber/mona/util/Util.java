package com.github.atelieramber.mona.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class Util {
    public static ITextComponent translatedFormattedText(String key, TextFormatting format) {
        return new StringTextComponent(format + (new TranslationTextComponent(key).getString()));
    }
}
