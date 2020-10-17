package com.github.atelieramber.mona.registries;

import com.github.atelieramber.mona.blocks.TestBlock;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class BlockRegistry {
    public static final class BlockRegistration<T extends Block> {
        RegistryObject<T> block;

        RegistryObject<BlockItem> blockItem;

        public BlockRegistration(String name, Class<T> c) {
            block = Registry.makeBlockRegistry(name, c);
            blockItem = Registry.makeBlockItem(block, new Item.Properties().group(Registry.MONA_TAB));
        }

        public BlockRegistration(String name, Class<T> c, ItemGroup group) {
            block = Registry.makeBlockRegistry(name, c);
            blockItem = Registry.makeBlockItem(block, new Item.Properties().group(group));
        }

        public T get() {
            return block.get();
        }

        public BlockItem item() {
            return blockItem.get();
        }
    }

    public static <T extends Block> BlockRegistration<T> makeRegistry(String name, Class<T> c) {
        return new BlockRegistration<T>(name, c);
    }

    public static final BlockRegistration<TestBlock> TEST_BLOCK = makeRegistry("test_block", TestBlock.class);

    public static void init() { /* Dummy */ }
}
