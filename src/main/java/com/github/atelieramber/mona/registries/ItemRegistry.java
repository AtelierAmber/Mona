package com.github.atelieramber.mona.registries;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ItemRegistry {

	public static <T extends Item> RegistryObject<T> makeRegistry(String name, Class<T> c) {
		return Registry.makeItemRegistry(name, c);
	}
	public static <T extends Item>  RegistryObject<T> makeRegistry(final String name, final Supplier<? extends T> sup){
		return Registry.makeItemRegistry(name, sup);
	}
	
	public static final RegistryObject<Item> TEST_ITEM = makeRegistry("test_item", () -> new Item(new Item.Properties().group(Registry.MONA_TAB)));
	
	public static void init() { /* Dummy */	}
}
