package com.github.atelieramber.mona.blocks.vault.tileentities;

import com.github.atelieramber.mona.registries.SoundRegistry;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;

public class VaultDoorTileEntity extends TileEntity implements ITickableTileEntity{
    public static final int DELAY = 10;
    public static final int DIAMOND_UP_DELAY = 60;
    public static final int UNLOCK_DURATION  = 2;
    public static final int DIAMOND_DURATION = 30;
    public static final int MIDDLE_DELAY = 20;
    public static final int MIDDLE_DURATION = 30;
    
    public static final int OPENING_DURATION = 80;
    public static final int BOTTOM_DURATION  = 50;
    
    public boolean opening = false;
    
    /* Opening progress in ticks */
    public int openingProgress = 0;
    
    public static float PROGRESS_PRE_TICK = 1/120.0f;
    
    public VaultDoorTileEntity(TileEntityType<VaultDoorTileEntity> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if(opening) {
            if(openingProgress == 0) {
                this.world.playSound(null, this.getPos(), SoundRegistry.VAULT_DOOR_RELEASE.get(), SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            int gate = VaultDoorTileEntity.UNLOCK_DURATION + VaultDoorTileEntity.DIAMOND_DURATION + VaultDoorTileEntity.DELAY*2;
            if(openingProgress == gate) {
                this.world.playSound(null, this.getPos(), SoundRegistry.VAULT_DOOR_OPEN.get(), SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            
            ++openingProgress;
        }
    }

}
