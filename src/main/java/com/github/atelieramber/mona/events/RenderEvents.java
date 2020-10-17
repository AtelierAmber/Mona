package com.github.atelieramber.mona.events;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.client.event.TextureStitchEvent;

//@OnlyIn(Dist.CLIENT)
public class RenderEvents {

    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if(!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            return;
        }
        
        //event.addSprite();
    }
    
}
