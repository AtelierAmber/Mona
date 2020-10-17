package com.github.atelieramber.mona.blocks.vault;

import com.github.atelieramber.mona.entities.EntityTags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockVoxelShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BaseVaultBlock extends Block {

    public BaseVaultBlock() {
        this(false);
    }
    public BaseVaultBlock(boolean breakable) {
        super(Properties.create(Material.IRON).hardnessAndResistance((breakable) ? 5.0f : -1.0F, (breakable) ? 6.0f : 3600000.0F).sound(SoundType.METAL).setRequiresTool());
    }
    public BaseVaultBlock(Properties properties) {
        this(false, properties);
    }
    public BaseVaultBlock(boolean breakable, Properties properties) {
        super(properties.hardnessAndResistance((breakable) ? 5.0f : -1.0F, (breakable) ? 6.0f : 3600000.0F).sound(SoundType.METAL).setRequiresTool());
    }

    protected static Boolean allowVaultMobs(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        if (entity.isContained(EntityTags.VAULT_MOBS)) {
            return true;
        }
        return false;
    }
    @Override
    public float getExplosionResistance() {
        return -1.0f;
    }
    @Override
    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        // TODO Auto-generated method stub
        return getExplosionResistance();
    }
    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        // TODO Auto-generated method stub
        super.onBlockExploded(state, world, pos, explosion);
    }
    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        // TODO Auto-generated method stub
        super.onExplosionDestroy(worldIn, pos, explosionIn);
    }
    
    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        return false;
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
    
}
