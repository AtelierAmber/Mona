package com.github.atelieramber.mona.blocks.vault;

import com.github.atelieramber.mona.blocks.vault.items.VaultDoorCreator;
import com.github.atelieramber.mona.blocks.vault.tileentities.VaultDoorDummyTileEntity;
import com.github.atelieramber.mona.blocks.vault.tileentities.VaultDoorTileEntity;
import com.github.atelieramber.mona.registries.BlockRegistry;
import com.github.atelieramber.mona.registries.BlockRegistry.BlockRegistration;
import com.github.atelieramber.mona.registries.ItemRegistry;
import com.github.atelieramber.mona.registries.TileEntityRegistry.TileEntityEntry;

import net.minecraftforge.fml.RegistryObject;

public class VaultRegistry {

    public static class Blocks {
        public static final BlockRegistration<VaultDoor> VAULT_DOOR = BlockRegistry.makeRegistry("vault_door", VaultDoor.class);
        public static final BlockRegistration<VaultDoorDummy> VAULT_DOOR_DUMMY = BlockRegistry.makeRegistry("vault_door_dummy", VaultDoorDummy.class);
        
        public static void init() { /* Dummy */ }
    }
    public static class TileEntities {
        public static final TileEntityEntry<VaultDoorDummyTileEntity> VAULT_DOOR_DUMMY = TileEntityEntry.create("vault_door_dummy_tile", 
                VaultRegistry.Blocks.VAULT_DOOR_DUMMY, VaultDoorDummyTileEntity::new);
        public static final TileEntityEntry<VaultDoorTileEntity> VAULT_DOOR = TileEntityEntry.create("vault_door_tile",       
                VaultRegistry.Blocks.VAULT_DOOR, VaultDoorTileEntity::new);
        
        public static void init() { /* Dummy */ }
    }
    public static class Items {
        public static final RegistryObject<VaultDoorCreator> VAULT_DOOR_CREATOR = ItemRegistry.makeRegistry("vault_door_creator", VaultDoorCreator.class);
        public static void init() { /* Dummy */ }
    }

    public static void init() { 
        Blocks.init();
        TileEntities.init();
        
        Items.init();
    }
}
