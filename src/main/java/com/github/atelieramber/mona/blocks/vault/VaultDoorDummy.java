package com.github.atelieramber.mona.blocks.vault;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class VaultDoorDummy extends BaseVaultBlock {
    public VaultDoorDummy() {
        super(Properties.create(Material.IRON).setAllowsSpawn((s, r, pos, e) -> false));
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Override
    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return VaultRegistry.TileEntities.VAULT_DOOR_DUMMY.get().create();
    }
}
