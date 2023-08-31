/*
 *  Dynamic Surroundings
 *  Copyright (C) 2020  OreCruncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package org.orecruncher.environs.shaders.aurora;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.lwjgl.opengl.GL11;
import org.orecruncher.environs.Environs;

import net.minecraft.client.renderer.RenderState.TargetState;
import net.minecraft.client.renderer.RenderState.TextureState;
import net.minecraft.client.renderer.RenderState.TransparencyState;

@OnlyIn(Dist.CLIENT)
public class AuroraRenderType extends RenderType {
    public AuroraRenderType(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
        super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
    }

    private static final TransparencyState AURORA_TRANSPARENCY = new TransparencyState(
            "aurora_transparency",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            },
            RenderSystem::disableBlend);

    private static final TargetState TARGET = WEATHER_TARGET;

    public static ResourceLocation TEXTURE = new ResourceLocation(Environs.MOD_ID,"textures/misc/aurora_band.png");

    public static final RenderType QUAD = create(
            "aurora_render_type",
            DefaultVertexFormats.POSITION_TEX,
            GL11.GL_QUADS,
            64,
            RenderType.State.builder()
                    .setTextureState(new TextureState(TEXTURE, false, false))
                    .setTransparencyState(AURORA_TRANSPARENCY)
                    .setOutputState(TARGET)
                    .setFogState(FOG)
                    .setShadeModelState(RenderState.SMOOTH_SHADE)
                    .setAlphaState(DEFAULT_ALPHA)
                    .setDepthTestState(LEQUAL_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setWriteMaskState(RenderState.COLOR_DEPTH_WRITE)
                    .createCompositeState(false));
}
