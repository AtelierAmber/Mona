package com.github.atelieramber.mona.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.github.atelieramber.mona.client.ter.VaultDoorRenderer;
import com.github.atelieramber.mona.util.rendering.ModelHandle;

import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class SpecialModelLoader extends ReloadListener<List<Pair<ResourceLocation, IUnbakedModel>>>{
    private static SpecialModelLoader INSTANCE;
    
    private Map<ResourceLocation, ModelHandle> modelHandles = new HashMap<>();
    
    public SpecialModelLoader() {
        INSTANCE = this;
    }
    
    public static SpecialModelLoader instance() {
        return INSTANCE;
    }
    
    @Override
    protected List<Pair<ResourceLocation, IUnbakedModel>> prepare(IResourceManager resourceManager, IProfiler profiler) {
        List<Pair<ResourceLocation, IUnbakedModel>> models = new ArrayList<>();
        
        loadModelToList(VaultDoorRenderer.TOP_CORNER, resourceManager, models);
        loadModelToList(VaultDoorRenderer.MAIN_HALF, resourceManager, models);
        loadModelToList(VaultDoorRenderer.TOP_DIAMOND, resourceManager, models);
        loadModelToList(VaultDoorRenderer.BOTTOM_HALF, resourceManager, models);
        loadModelToList(VaultDoorRenderer.MIDDLE, resourceManager, models);
        
        return models;
    }

    private void loadModelToList(ResourceLocation loc, IResourceManager resourceManager, List<Pair<ResourceLocation, IUnbakedModel>> models) {
        models.add(Pair.of(loc, loadUnbakedModel(loc, resourceManager)));
    }
    
    private IUnbakedModel loadUnbakedModel(ResourceLocation loc, IResourceManager resourceManager) {
        InputStream stream;
        try {
            stream = resourceManager.getResource(loc).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return BlockModel.deserialize(new InputStreamReader(stream));
    }
    
    @Override
    protected void apply(List<Pair<ResourceLocation, IUnbakedModel>> models, IResourceManager resourceManager, IProfiler profiler) {
        // Clear existing cache
        modelHandles.clear();
        
        for(Pair<ResourceLocation, IUnbakedModel> model : models) {
            modelHandles.put(model.getLeft(), new ModelHandle(model.getLeft(), model.getRight()));
        }
    }
    
    public ModelHandle getHandle(ResourceLocation location) {
        return modelHandles.get(location);
    }
}
