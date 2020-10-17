package com.github.atelieramber.mona.events.client;

import com.github.atelieramber.mona.client.SpecialModelLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;

public class ResourceReloader {
    public static void register() {
        Minecraft mc = Minecraft.getInstance();
        ((IReloadableResourceManager)mc.getResourceManager()).addReloadListener(new SpecialModelLoader());
    }
}
