package com.github.atelieramber.mona.util.rendering;

import com.github.atelieramber.mona.util.rendering.PrimitiveUtil2d.Vec2f;
import com.github.atelieramber.mona.util.rendering.PrimitiveUtil3d.Cube;
import com.github.atelieramber.mona.util.rendering.PrimitiveUtil3d.Rect;
import com.github.atelieramber.mona.util.rendering.TextureUtil.DynamicTextureState;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class RenderUtil {
    
    public static class RenderCube{
        public Cube base;
        
        public static float[] rectUVs = new float[] {
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 0.0f
            };;
        public float[] rectColors;
        
        
        private RenderCube(Cube cube) {
            base = cube;
        }
        
        public static RenderCube create(Cube cube) {
            return new RenderCube(cube);
        }
        
        public static RenderCube create(float size, float x, float y, float z) {
            return create(Cube.create(size, x, y, z));
        }
        
        public static RenderCube create(float size) {
            return create(Cube.create(size));
        }
    }
    
    public static void renderFullFlatCube(Cube cube, BlockPos pos, MatrixStack matrixStack, IVertexBuilder builder, World world, int combinedLight, int combinedOverlay, float r, float g, float b, float a) {        
        Matrix4f mat  = matrixStack.getLast().getMatrix();
        Matrix3f norm = matrixStack.getLast().getNormal();
        
        for(int i = 0; i < 6; ++i) {
            Rect rect = cube.rects[i];
            combinedLight = getPackedLight(world, pos.offset(rect.facingDir));
            for(int j = 0; j < 4; ++j) {
                Vector3d vert = rect.verts[j];
                builder.pos(mat, (float)vert.x, (float)vert.y, (float)vert.z)
                       .color(r, g, b, a)
                       .tex(0.0f, 0.0f)
                       .lightmap(combinedLight)
                       .normal(norm, (float)rect.facing.x, (float)rect.facing.y, (float)rect.facing.z)
                       .endVertex();
            }
        }
    }
    public static void renderDynamicTexturedFullCube(Cube cube, BlockPos pos, MatrixStack matrixStack, IVertexBuilder builder, World world, DynamicTextureState texture, int spriteIndex, int combinedLight, int combinedOverlay, float r, float g, float b, float a) {        
        Matrix4f mat  = matrixStack.getLast().getMatrix();
        Matrix3f norm = matrixStack.getLast().getNormal();
        
        PrimitiveUtil2d.Rect uvRect = texture.getUVsFor(spriteIndex);
        
        for(int i = 0; i < 6; ++i) {
            Rect rect = cube.rects[i];
            combinedLight = getPackedLight(world, pos.offset(rect.facingDir));
            for(int j = 0; j < 4; ++j) {
                Vector3d vert = rect.verts[j];
                Vec2f uv = uvRect.verts[j];
                builder.pos(mat, (float)vert.x, (float)vert.y, (float)vert.z)
                       .color(r, g, b, a)
                       .tex(uv.x, uv.y)
                       .lightmap(combinedLight)
                       .normal(norm, (float)rect.facing.x, (float)rect.facing.y, (float)rect.facing.z)
                       .endVertex();
            }
        }
    }

    public static int getPackedLight(World world, BlockPos pos)
    {
        int blockLight = world.getLightFor(LightType.BLOCK, pos);
        int skyLight = world.getLightFor(LightType.SKY, pos);
        return LightTexture.packLight(blockLight, skyLight);
    }
}
