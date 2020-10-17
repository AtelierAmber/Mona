package com.github.atelieramber.mona.events.client;

import com.github.atelieramber.mona.Mona;

import net.minecraftforge.client.event.ModelRegistryEvent;

public class ModelEvents {
    public static void registryEvent(ModelRegistryEvent event) {
        Mona.LOGGER.debug("Loading special models...");
        //ModelLoader.addSpecialModel(VaultDoorRenderer.MODEL);
    }
}
