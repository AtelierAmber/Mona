package com.github.atelieramber.mona;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.atelieramber.mona.events.RenderEvents;
import com.github.atelieramber.mona.events.client.ModelEvents;
import com.github.atelieramber.mona.events.client.ResourceReloader;
import com.github.atelieramber.mona.registries.BlockRegistry;
import com.github.atelieramber.mona.registries.Registry;
import com.github.atelieramber.mona.registries.TileEntityRegistry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Mona.MODID)
public class Mona {
    public static final String MODID = "mona";
    public static final String NAME = "Mona";
    public static final String VERSION = "0.0.1";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // JsonDataManager dataManager;

    private static Mona instance;

    public Mona() {
        instance = this;

        if(FMLEnvironment.dist == Dist.CLIENT) {
            ResourceReloader.register();
        }
        
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::clientSetup);
        
        modEventBus.addListener(ModelEvents::registryEvent);
        // modEventBus.addListener(this::onServerStarting);

        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        forgeEventBus.addListener(this::onDataPackReload);
        forgeEventBus.addListener(RenderEvents::onTextureStitch);
        
        // ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Mona.SPEC);

        Registry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static Mona Instance() {
        return instance;
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockRegistry.TEST_BLOCK.get(), RenderType.getTranslucent());
        TileEntityRegistry.initRenderers();
    }

    public void onServerStarting(FMLServerStartingEvent event) {

    }

    public void onDataPackReload(final AddReloadListenerEvent event) {
        // dataManager = new JsonDataManager();
        // event.addListener(dataManager);
        // System.out.println("Registered Data loader.");
    }
}
