package com.github.atelieramber.mona.registries;

import java.util.function.Supplier;

import com.github.atelieramber.mona.Mona;
import com.github.atelieramber.mona.blocks.vault.VaultRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mona.MODID);
    protected static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mona.MODID);
    protected static final DeferredRegister<TileEntityType<? extends TileEntity>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Mona.MODID);
    protected static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Mona.MODID);

    protected static <T extends Block> RegistryObject<T> makeBlockRegistry(final String name, Class<T> c) {
        return BLOCKS.register(name, () -> {
            try {
                return c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    protected static <T extends Block> RegistryObject<T> makeBlockRegistry(final String name, final Supplier<? extends T> sup) {
        return BLOCKS.register(name, sup);
    }
    protected static <T extends Block> RegistryObject<BlockItem> makeBlockItem(RegistryObject<T> block) {
        return ITEMS.register("blockitem/" + block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
    protected static <T extends Block> RegistryObject<BlockItem> makeBlockItem(RegistryObject<T> block, Item.Properties properties) {
        return ITEMS.register("blockitem/" + block.getId().getPath(), () -> new BlockItem(block.get(), properties));
    }
    protected static <T extends Item> RegistryObject<T> makeItemRegistry(String name, Class<T> c) {
        return ITEMS.register(name, () -> {
            try {
                return c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    protected static <T extends Item> RegistryObject<T> makeItemRegistry(final String name, final Supplier<? extends T> sup) {
        return ITEMS.register(name, sup);
    }
    protected static RegistryObject<SoundEvent> makeSoundRegistry(final String name){
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Mona.MODID, name)));
    }
    
    
    public static final ItemGroup MONA_TAB = new ItemGroup("mona") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockRegistry.TEST_BLOCK.get());
        }
    };

    public static void register(IEventBus modEventBus) {
        ItemRegistry.init();
        BlockRegistry.init();
        
        TileEntityRegistry.init(modEventBus);
        
        SoundRegistry.init();
        
        VaultRegistry.init();

        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITY_TYPES.register(modEventBus);
        SOUNDS.register(modEventBus);

        Mona.LOGGER.debug("Registration complete.");
    }
}
