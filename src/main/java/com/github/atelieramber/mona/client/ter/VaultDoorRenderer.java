package com.github.atelieramber.mona.client.ter;

import com.github.atelieramber.mona.Mona;
import com.github.atelieramber.mona.blocks.vault.tileentities.VaultDoorTileEntity;
import com.github.atelieramber.mona.client.CustomRenderTypes;
import com.github.atelieramber.mona.client.SpecialModelLoader;
import com.github.atelieramber.mona.util.rendering.ModelHandle;
import com.github.atelieramber.mona.util.rendering.PrimitiveUtil3d.Cube;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class VaultDoorRenderer extends TileEntityRenderer<VaultDoorTileEntity> {

    public static ResourceLocation TOP_CORNER = new ResourceLocation(Mona.MODID, "models/block/vault_door_top_corner.json");
    public static ResourceLocation MAIN_HALF = new ResourceLocation(Mona.MODID, "models/block/vault_door_main_half.json");
    public static ResourceLocation TOP_DIAMOND = new ResourceLocation(Mona.MODID, "models/block/vault_door_top_diamond.json");
    public static ResourceLocation BOTTOM_HALF = new ResourceLocation(Mona.MODID, "models/block/vault_door_bottom_half.json");
    public static ResourceLocation MIDDLE = new ResourceLocation(Mona.MODID, "models/block/vault_door_middle.json");
    
    public VaultDoorRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }
    
    static Cube cube = Cube.create(.75f, .5f, .5f,  .5f);
    
    @Override
    public void render(VaultDoorTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        IVertexBuilder builder = buffer.getBuffer(CustomRenderTypes.VAULT_BLOCK);
        
        renderDoor(tileEntity, partialTicks, matrixStack, builder, combinedLight, combinedOverlay);
    }
    
    private void renderDoor(VaultDoorTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IVertexBuilder builder, int combinedLight, int combinedOverlay) {
        float animProgress = tileEntity.opening ? tileEntity.openingProgress + partialTicks : 0.0f;
        
        ModelHandle model;
        float gate;
        
        matrixStack.push();
        matrixStack.scale(1.0f, 1.0f, 0.5f);
        matrixStack.translate(0.0f, 0.0f, .5f);
        
        gate = VaultDoorTileEntity.UNLOCK_DURATION;
        float unlockProgress = (Math.min(animProgress, VaultDoorTileEntity.UNLOCK_DURATION)/VaultDoorTileEntity.UNLOCK_DURATION) * .125f;
        
        matrixStack.push();
        matrixStack.scale(1.0f, 1.0f, .875f + unlockProgress);
        matrixStack.translate(1.0f, 1.0f, 0.0f);
        model = SpecialModelLoader.instance().getHandle(TOP_CORNER);
        model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        
        matrixStack.push();
        matrixStack.rotate(new Quaternion(Vector3f.YP, 180, true));
        matrixStack.scale(1.0f, 1.0f, .875f + unlockProgress);
        matrixStack.translate(1.0f, 1.0f, -1.0f);
        model = SpecialModelLoader.instance().getHandle(TOP_CORNER);
        model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        
        gate = VaultDoorTileEntity.UNLOCK_DURATION + VaultDoorTileEntity.DIAMOND_DURATION + VaultDoorTileEntity.DIAMOND_UP_DELAY + VaultDoorTileEntity.DELAY*2;
        if(animProgress < gate) {
            gate = VaultDoorTileEntity.UNLOCK_DURATION + VaultDoorTileEntity.DELAY;
            float diamondProgress = (animProgress > gate) ? (Math.min(animProgress - gate, VaultDoorTileEntity.DIAMOND_DURATION)/VaultDoorTileEntity.DIAMOND_DURATION) * -.25f : 0.0f;
            
            gate = VaultDoorTileEntity.UNLOCK_DURATION + VaultDoorTileEntity.DIAMOND_DURATION + VaultDoorTileEntity.DELAY*2 + VaultDoorTileEntity.MIDDLE_DELAY;
            float diamondUpProgress = (animProgress > gate) ? (Math.min(animProgress - gate, VaultDoorTileEntity.MIDDLE_DURATION)/VaultDoorTileEntity.MIDDLE_DURATION) * -1.0f : 0.0f;
        
            matrixStack.push();
            matrixStack.translate(-0.5f, 0.0f, 0.0f);
            matrixStack.scale(1.0f, 1.0f, 1.0f + diamondProgress);
            model = SpecialModelLoader.instance().getHandle(TOP_DIAMOND);
            model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.translate(0.0f, 0.0f - diamondUpProgress + (-diamondUpProgress*.0625f), 0.0f);
            matrixStack.scale(1.0f, 1.0f + diamondUpProgress, 1.0f);
            model = SpecialModelLoader.instance().getHandle(MIDDLE);
            model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
        
        gate = VaultDoorTileEntity.UNLOCK_DURATION + VaultDoorTileEntity.DIAMOND_DURATION + VaultDoorTileEntity.DELAY*2;
        float x = (animProgress > gate) ? (Math.min(animProgress - gate, VaultDoorTileEntity.OPENING_DURATION)/VaultDoorTileEntity.OPENING_DURATION) : 0.0f;
        float openingProgress = smoothstep(x) * 2.5f;
        
        matrixStack.push();
        matrixStack.translate(0.0f, -0.25f + openingProgress, 0.0f);
        model = SpecialModelLoader.instance().getHandle(MAIN_HALF);
        model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        
        matrixStack.push();
        matrixStack.rotate(new Quaternion(Vector3f.YP, 180, true));
        matrixStack.translate(0.0f, -0.25f + openingProgress, -1.0f);
        model = SpecialModelLoader.instance().getHandle(MAIN_HALF);
        model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        
        gate = VaultDoorTileEntity.UNLOCK_DURATION + VaultDoorTileEntity.DIAMOND_DURATION + VaultDoorTileEntity.DELAY*2;
        x = (animProgress > gate) ? (Math.min(animProgress - gate, VaultDoorTileEntity.BOTTOM_DURATION)/VaultDoorTileEntity.BOTTOM_DURATION) : 0.0f;
        float bottomProgress = smoothstep(x) * -1.5f;
        
        matrixStack.push();
        matrixStack.rotate(new Quaternion(Vector3f.YP, 180, true));
        matrixStack.translate(0.0f, -2.0f + bottomProgress, -1.0f);
        model = SpecialModelLoader.instance().getHandle(BOTTOM_HALF);
        model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        
        matrixStack.push();
        matrixStack.translate(0.0f, -2.0f + bottomProgress, 0.0f);
        model = SpecialModelLoader.instance().getHandle(BOTTOM_HALF);
        model.render(builder, matrixStack, combinedLight, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        
        matrixStack.pop();
    }
    
    private float smoothstep(float x) {
        return smoothstep(0.0f, 1.0f, x);
    }
    private float smoothstep(float edge0, float edge1, float x) {
        x = MathHelper.clamp((x- edge0)/ (edge1-edge0), 0.0f, 1.0f);
        return x * x * x * (x * (x * 6f - 15f) + 10f);
    }
    
}
