package net.bunnycraft.mixin.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Shadow
    private static float red;
    @Shadow
    private static float green;
    @Shadow
    private static float blue;
    @Shadow
    private static long lastWaterFogColorUpdateTime = -1L;

    @Unique
    private static boolean inSculk = false;

    @Inject(
            method = "render(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/world/ClientWorld;IF)V",
            at = @At("TAIL")
    )
    private static void SculkFogColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        if (world.getBlockState(camera.getBlockPos()).isOf(Blocks.SCULK)) {
            red = 0.1F;
            green = 0.2F;
            blue = 0.2F;
            lastWaterFogColorUpdateTime = -1L;
            inSculk = true;
            RenderSystem.clearColor(red, green, blue, 0.0F);
        } else {
            inSculk = false;
        }
    }


    @Inject(
            method = "applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void applySculkFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();
        if (inSculk) {
            BackgroundRenderer.FogData fogData = new BackgroundRenderer.FogData(fogType);

            ci.cancel();
            if (entity.isSpectator()) {
                fogData.fogStart = -8.0F;
                fogData.fogEnd = viewDistance * 0.5F;
            } else {
                fogData.fogStart = 0.0F;
                fogData.fogEnd = 5.0F;
                fogData.fogShape = FogShape.SPHERE;
            }

            RenderSystem.setShaderFogStart(fogData.fogStart);
            RenderSystem.setShaderFogEnd(fogData.fogEnd);
            RenderSystem.setShaderFogShape(fogData.fogShape);
        }
    }
}
