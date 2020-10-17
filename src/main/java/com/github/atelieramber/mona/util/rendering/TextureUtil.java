package com.github.atelieramber.mona.util.rendering;

import java.io.IOException;

import com.github.atelieramber.mona.Mona;
import com.github.atelieramber.mona.util.rendering.PrimitiveUtil2d.Rect;
import com.github.atelieramber.mona.util.rendering.PrimitiveUtil2d.Vec2f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class TextureUtil {
    public static final DynamicTextureState VAULT_BLOCK_SHEET = new DynamicTextureState("vault_block_sheet", 16*4, 16*4);
    
    // Referencing TextureManager$getDynamicTextureLocation
    public static class DynamicTextureState{
        public String name;
        public ResourceLocation location;
        public RenderState.TextureState state;
        public DynamicTexture texture;
        
        protected int width = 16, height = 16;
        
        protected int spriteCount = 1;
        protected int spriteWidth = 16, spriteHeight = 16;
        protected float spriteWidthUV = 1.0f, spriteHeightUV = 1.0f;
        protected int spriteCountX = 1, spriteCountY = 1;
        
        public DynamicTextureState(String name) {
            this.name = name;
            
            location = new ResourceLocation(Mona.MODID, "dynamic/" + name);
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            texture = new DynamicTexture(width, height, true);
            try(IResource res = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("mona", "textures/blocks/artifact_door.png"))) {
                NativeImage img = NativeImage.read(res.getInputStream());
                //img.flip();
                img.flip();
                texture.setTextureData(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            texture.updateDynamicTexture();
            textureManager.loadTexture(location, texture);
            state = new RenderState.TextureState(location, false, false);
        }
        
        /* Assumes 16 pixel sprite width and height */
        public DynamicTextureState(String name, int width, int height) {
            this.name = name;
            this.width = width;
            this.height = height;
            this.spriteCountX = this.width/this.spriteWidth;
            this.spriteCountY = this.height/this.spriteHeight;
            this.spriteCount = this.spriteCountX * this.spriteCountY;
            this.spriteWidthUV = this.spriteWidth/(float)this.width;
            this.spriteHeightUV = this.spriteHeight/(float)this.height;
            
            location = new ResourceLocation(Mona.MODID, "dynamic/" + name);
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            texture = new DynamicTexture(width, height, true);
            try(IResource res = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("mona", "textures/blocks/artifact_door.png"))) {
                texture.setTextureData(NativeImage.read(res.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            texture.updateDynamicTexture();
            textureManager.loadTexture(location, texture);
            state = new RenderState.TextureState(location, false, false);
        }
        
        public DynamicTextureState(String name, int width, int height, int spriteWidth, int spriteHeight) {
            this.name = name;
            this.width = width;
            this.height = height;
            this.spriteWidth = spriteWidth;
            this.spriteHeight = spriteHeight;
            this.spriteCountX = this.width/this.spriteWidth;
            this.spriteCountY = this.height/this.spriteHeight;
            this.spriteCount = this.spriteCountX * this.spriteCountY;
            this.spriteWidthUV = this.spriteWidth/(float)this.width;
            this.spriteHeightUV = this.spriteHeight/(float)this.height;
            
            location = new ResourceLocation(Mona.MODID, "dynamic/" + name);
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            texture = new DynamicTexture(width, height, true);
            try(IResource res = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("mona", "textures/blocks/artifact_door.png"))) {
                texture.setTextureData(NativeImage.read(res.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            texture.updateDynamicTexture();
            textureManager.loadTexture(location, texture);
            state = new RenderState.TextureState(location, false, false);
        }
        
        public Rect getUVsFor(int spriteIndex) {
            if(spriteIndex > spriteCount || spriteIndex < 0) {
                throw new IndexOutOfBoundsException();
            }
            int x = spriteIndex % spriteCountX;
            int y = spriteIndex / spriteCountY;
            float minX = x * spriteWidthUV;
            float maxX = (x+1) * spriteWidthUV;
            float minY = y * spriteHeightUV;
            float maxY = (y+1) * spriteHeightUV;
            return Rect.create(Vec2f.v(minX, minY), Vec2f.v(maxX, minY), Vec2f.v(minX, maxY), Vec2f.v(maxX, maxY));
        }
        
        public void setRegion(PrimitiveUtil2d.Rect bounds, int color) {
            NativeImage img = texture.getTextureData();
            for(int i = (int) bounds.v1.x; i < bounds.v2.x; ++i) {
                for(int j = (int) bounds.v1.y; j < bounds.v3.y; ++j) {
                    img.setPixelRGBA(i, j, color);
                }
            }
            texture.updateDynamicTexture();
        }
        public void setRegion(PrimitiveUtil2d.Rect bounds, NativeImage image) {
            NativeImage img = texture.getTextureData();
            int x = 0;
            int y = 0;
            for(int i = (int) bounds.v1.x; i < bounds.v2.x; ++i) {
                for(int j = (int) bounds.v1.y; j < bounds.v3.y; ++j) {
                    img.setPixelRGBA(i, j, img.getPixelRGBA(x, y));
                    ++y;
                }
                ++x;
            }
        }
        public void setRegion(PrimitiveUtil2d.Rect bounds, NativeImage image, int imageX, int imageY) {
            NativeImage img = texture.getTextureData();
            int x = imageX;
            int y = imageY;
            for(int i = (int) bounds.v1.x; i < bounds.v2.x; ++i) {
                for(int j = (int) bounds.v1.y; j < bounds.v3.y; ++j) {
                    img.setPixelRGBA(i, j, img.getPixelRGBA(x, y));
                    ++y;
                }
                ++x;
            }
        }        
    }
    
    public static int colorToInt(int r, int g, int b) {
        int ret = 0;
        ret += r;
        ret = ret << 8;
        ret += g;
        ret = ret << 8;
        ret += b;
        return ret;
    }
    public static int colorToInt(int r, int g, int b, int a) {
        int ret = 0;
        ret += r;
        ret = ret << 8;
        ret += g;
        ret = ret << 8;
        ret += b;
        ret = ret << 8;
        ret += a;
        return ret;
    }
}
