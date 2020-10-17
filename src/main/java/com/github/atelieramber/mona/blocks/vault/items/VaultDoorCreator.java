package com.github.atelieramber.mona.blocks.vault.items;

import java.util.ArrayList;
import java.util.List;

import com.github.atelieramber.mona.blocks.vault.VaultRegistry;
import com.github.atelieramber.mona.blocks.vault.tileentities.VaultDoorDummyTileEntity;
import com.github.atelieramber.mona.registries.Registry;

import net.minecraft.block.BlockState;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VaultDoorCreator extends Item {

    public VaultDoorCreator() {
        super(new Item.Properties().group(Registry.MONA_TAB));
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        if(!world.isRemote) {
            BlockState state = world.getBlockState(context.getPos());
            BlockState upState = world.getBlockState(context.getPos().up());
            if((upState.isAir() || upState.isReplaceable(new DirectionalPlaceContext(world, context.getPos().up(), Direction.UP, ItemStack.EMPTY, Direction.DOWN))) &&
                    state.isSolidSide(world, context.getPos(), context.getFace())) {
                List<BlockPos> dummyPlacements = new ArrayList<BlockPos>();
                BlockPos.Mutable temp = context.getPos().up().toMutable();
                for(int x = 0; x < 4; ++x) {
                    for(int y = 0; y < 4; ++y) {
                        temp = temp.setAndOffset(context.getPos().up(), x, y, 0);
                        if(x == 0 && y == 0) continue;
                        state = world.getBlockState(temp);
                        if(!state.isAir() || state.isReplaceable(new DirectionalPlaceContext(world, temp, Direction.UP, ItemStack.EMPTY, Direction.DOWN))) {
                            dummyPlacements.add(temp.toImmutable());
                        }else {
                            return super.onItemUseFirst(stack, context);
                        }
                    }
                }
                world.setBlockState(context.getPos().up(), VaultRegistry.Blocks.VAULT_DOOR.get().getDefaultState());
                for(BlockPos pos : dummyPlacements) {
                    world.setBlockState(pos, VaultRegistry.Blocks.VAULT_DOOR_DUMMY.get().getDefaultState());
                    VaultDoorDummyTileEntity te = (VaultDoorDummyTileEntity) world.getTileEntity(pos);
                    te.setParent(context.getPos().up());
                    ItemStack s;
                    s = new ItemStack(this);
                }
            }
            return super.onItemUseFirst(stack, context);
        }
        return super.onItemUseFirst(stack, context);
    }
}
