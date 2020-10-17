package com.github.atelieramber.mona.client;

import org.lwjgl.opengl.GL11;

import com.github.atelieramber.mona.util.rendering.TextureUtil;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class CustomRenderTypes extends RenderType{

    public CustomRenderTypes(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_,
            boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }
    
    public static final RenderType VAULT_BLOCK = makeType("mona:vault_block",
            DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 65536,
            RenderType.State.getBuilder()
                .shadeModel(SHADE_ENABLED)
                .diffuseLighting(DiffuseLightingState.DIFFUSE_LIGHTING_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .texture(TextureUtil.VAULT_BLOCK_SHEET.state)
                .overlay(OverlayState.OVERLAY_DISABLED)
                .alpha(AlphaState.HALF_ALPHA)
                .build(false));
    
    public static final RenderType MAGIC_CIRCLE = makeType("mona:magic_circle",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, 256,
            RenderType.State.getBuilder()
                .layer(field_239235_M_)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .texture(TextureUtil.VAULT_BLOCK_SHEET.state)
                .depthTest(DEPTH_ALWAYS)
                .cull(CULL_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .writeMask(COLOR_WRITE)
                .build(false));
    
}
