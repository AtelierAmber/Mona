package com.github.atelieramber.mona.blocks.vault.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class VaultDoorDummyTileEntity extends TileEntity {

    public VaultDoorDummyTileEntity(final TileEntityType<VaultDoorDummyTileEntity> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    private VaultDoorTileEntity parent;
    private BlockPos parentPos;
    
    public void setParent(BlockPos pos) {
        if(parent != null) {
            parent = null;
        }
        parentPos = pos;
    }
    
    public VaultDoorTileEntity getParent(ServerWorld world) {
        if(parent == null) {
            parent = (VaultDoorTileEntity)world.getTileEntity(parentPos);
        }
        return parent;
    }
    
    /**************/
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putLong("parent_pos", parentPos.toLong());
        return super.write(compound);
    }
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        parentPos = BlockPos.fromLong(nbt.getLong("parent_pos"));
        super.read(state, nbt);
    }
    @Override
    public CompoundNBT getUpdateTag() {
        return write(super.getUpdateTag());
    }
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
        super.handleUpdateTag(state, tag);
    }
}