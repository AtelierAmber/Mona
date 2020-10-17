package com.github.atelieramber.mona.registries;

import java.util.function.Function;
import java.util.function.Supplier;

import com.github.atelieramber.mona.blocks.vault.VaultRegistry;
import com.github.atelieramber.mona.client.ter.VaultDoorRenderer;
import com.github.atelieramber.mona.registries.BlockRegistry.BlockRegistration;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TileEntityRegistry {
    private static class MutableRegistration<T extends TileEntity> {
        RegistryObject<TileEntityType<T>> content;

        public void set(RegistryObject<TileEntityType<T>> value) {
            content = value;
        }
        public TileEntityType<T> get() {
            return content.get();
        }

        public Supplier<TileEntityType<T>> asSupplier(final NonNullFunction<TileEntityType<T>, ? extends T> factory, BlockRegistration<? extends Block> block) {
            return () -> {
                RegistryObject<TileEntityType<T>> reg = content;
                return TileEntityType.Builder.<T>create(() -> factory.apply(reg.get()), block.get()).build(null);
            };
        }
    }

    public static final class TileEntityEntry<T extends TileEntity> {
        private MutableRegistration<T> registry = new MutableRegistration<T>();
        private TileEntityEntry(String name, BlockRegistration<? extends Block> block, final NonNullFunction<TileEntityType<T>, ? extends T> factory) {
            registry.set(Registry.TILE_ENTITY_TYPES.register(name, registry.asSupplier(factory, block)));
        }

        public TileEntityType<T> get() {
            return registry.get();
        }
        
        //@OnlyIn(Dist.CLIENT)
        public void addRenderer(Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<T>> factory) {
            ClientRegistry.bindTileEntityRenderer(registry.get(), factory);
        }

        public static final <T extends TileEntity> TileEntityEntry<T> create(final String name, final BlockRegistration<? extends Block> block, final NonNullFunction<TileEntityType<T>, ? extends T> constructor) {
            return new TileEntityEntry<T>(name, block, constructor);
        }
    }

    protected static void init(IEventBus modEventBus) { /* Dummy */ }
    
    public static void initRenderers() {
        VaultRegistry.TileEntities.VAULT_DOOR.addRenderer(VaultDoorRenderer::new);
    }

}
