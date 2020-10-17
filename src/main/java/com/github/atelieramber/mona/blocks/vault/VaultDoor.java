package com.github.atelieramber.mona.blocks.vault;

import javax.annotation.Nullable;

import com.github.atelieramber.mona.blocks.vault.tileentities.VaultDoorTileEntity;
import com.github.atelieramber.mona.util.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class VaultDoor extends BaseVaultBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    // TODO: Add facing
    
    private static boolean interacted = false;

    public VaultDoor() {
        super(Properties.create(Material.IRON).setAllowsSpawn((s, r, pos, e) -> false).notSolid().doesNotBlockMovement());
        setDefaultState(getDefaultState().with(OPEN, Boolean.FALSE));
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        VaultDoorTileEntity te = (VaultDoorTileEntity) world.getTileEntity(pos);
        te.opening = true;
        te.openingProgress = 0;
        return this.onBlockClickedAction(state, world, pos, player);
    }
    
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        this.onBlockClickedAction(state, worldIn, pos, player);
    }
    
    protected ActionResultType onBlockClickedAction(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        // TODO: Ensure sent only once. Store in player data and record to book.
        if(worldIn.isRemote && !interacted) {
            interacted = true;
            ITextComponent text = Util.translatedFormattedText("vault.observe.door", TextFormatting.ITALIC);
            player.sendMessage(text, player.getUniqueID());
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }
    
    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }
    
    /* 4x4 space available. This should never throw false as this door is only generated.*/
    // TODO: Currently only assumes north facing
//    @Override
//    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
//        // pos is bottom left of door
//        for(int x = 0; x < 4; ++x) {
//            for(int y = 0; y < 4; ++y) {
//                
//            }
//        }
//        return false;
//    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        // TODO Auto-generated method stub
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new VaultDoorTileEntity(VaultRegistry.TileEntities.VAULT_DOOR.get());
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    
    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
    }
}
