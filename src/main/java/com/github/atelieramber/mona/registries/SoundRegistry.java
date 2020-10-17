package com.github.atelieramber.mona.registries;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class SoundRegistry {
    public static final RegistryObject<SoundEvent> VAULT_DOOR_RELEASE = Registry.makeSoundRegistry("vault_door_release");
    public static final RegistryObject<SoundEvent> VAULT_DOOR_OPEN = Registry.makeSoundRegistry("vault_door_open");
    
    public static void init() { /* Dummy */ }
}
