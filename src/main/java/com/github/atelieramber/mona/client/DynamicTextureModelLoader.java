package com.github.atelieramber.mona.client;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;

public class DynamicTextureModelLoader extends ModelBakery{

    
    protected DynamicTextureModelLoader(IResourceManager resourceManagerIn, BlockColors blockColorsIn, boolean vanillaBakery) {
        super(resourceManagerIn, blockColorsIn, vanillaBakery);
    }
    
    public DynamicTextureModelLoader(IResourceManager resourceManagerIn, BlockColors blockColorsIn, IProfiler profilerIn, int maxMipmapLevel) {
        super(resourceManagerIn, blockColorsIn, profilerIn, maxMipmapLevel);
        // TODO Auto-generated constructor stub
    }


}
