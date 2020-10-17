package com.github.atelieramber.mona.util.rendering;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.github.atelieramber.mona.Mona;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;

public class ModelHandle
{
    private final Random rand = new Random();
    private final IBakedModel model;

    public static ModelHandle of(String modelLocation, IUnbakedModel model)
    {
        return new ModelHandle(new ResourceLocation(modelLocation), model);
    }

    public ModelHandle(ResourceLocation modelLocation, IUnbakedModel model)
    {
        this.model = model.bakeModel(ModelLoader.instance(), FakeSprite.GETTER, ModelRotation.X0_Y0, modelLocation);
        rand.setSeed(42L);
    }

    public void render(IRenderTypeBuffer bufferIn, RenderType rt, MatrixStack matrixStackIn, int packedLightIn, int color)
    {
        render(bufferIn, rt, matrixStackIn, packedLightIn, OverlayTexture.NO_OVERLAY, color);
    }

    public void render(IRenderTypeBuffer bufferIn, RenderType rt, MatrixStack matrixStackIn, int packedLightIn, int overlay, int color)
    {
        float a = ((color >> 24) & 0xFF) / 255.0f;
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color >> 0) & 0xFF) / 255.0f;

        IVertexBuilder bb = bufferIn.getBuffer(rt);
        render(bb, matrixStackIn, packedLightIn, overlay, r, g, b, a);
    }
    public void render(IVertexBuilder builder, MatrixStack matrixStackIn, int combinedLight, float r, float g, float b, float a)
    {
        render(builder, matrixStackIn, combinedLight, OverlayTexture.NO_OVERLAY, r, g, b, a);
    }
    public void render(IVertexBuilder builder, MatrixStack matrixStack, int combinedLight, int combinedOverlay, float r, float g, float b, float a)
    {
        MatrixStack.Entry mat = matrixStack.getLast();
        for (Direction direction : Direction.values()) {
            renderQuads(mat, builder, r, g, b, model.getQuads(null, direction, rand, EmptyModelData.INSTANCE), combinedLight, combinedOverlay);
        }
        
        renderQuads(mat, builder, r, g, b, model.getQuads(null, null, rand, EmptyModelData.INSTANCE), combinedLight, combinedOverlay);
    }

    private void renderQuads(MatrixStack.Entry matrixEntry, IVertexBuilder buffer, float red, float green, float blue, List<BakedQuad> listQuads, int combinedLight, int combinedOverlay) {
        for (BakedQuad bakedquad : listQuads) {
            float r;
            float g;
            float b;
            if (bakedquad.hasTintIndex()) {
                r = MathHelper.clamp(red, 0.0F, 1.0F);
                g = MathHelper.clamp(green, 0.0F, 1.0F);
                b = MathHelper.clamp(blue, 0.0F, 1.0F);
            } else {
                r = 1.0F;
                g = 1.0F;
                b = 1.0F;
            }

            buffer.addQuad(matrixEntry, bakedquad, r, g, b, combinedLight, combinedOverlay);
        }

    }
    
    public IBakedModel model() {
        return model;
    }

    private static class FakeSprite extends TextureAtlasSprite
    {
        public static final ResourceLocation LOCATION = new ResourceLocation(Mona.MODID, "fake");
        public static final FakeSprite INSTANCE = new FakeSprite();
        public static final Function<RenderMaterial, TextureAtlasSprite> GETTER = (x) -> INSTANCE;

        protected FakeSprite()
        {
            super(null,
                    new Info(LOCATION, 16, 16, AnimationMetadataSection.EMPTY),
                    0, 1, 1,
                    0, 0, new NativeImage(16, 16, false));
        }

        @Override
        public float getInterpolatedU(double u)
        {
            return (float) u / 16;
        }

        @Override
        public float getInterpolatedV(double v)
        {
            return (float) v / 16;
        }
        @Override
        public float getUvShrinkRatio() {
            return 0.0f;
        }
        
    }
}
