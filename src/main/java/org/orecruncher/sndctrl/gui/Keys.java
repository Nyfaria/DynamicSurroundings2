/*
 * Dynamic Surroundings
 * Copyright (C) 2020  OreCruncher
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
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>
 */
package org.orecruncher.sndctrl.gui;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.orecruncher.lib.GameUtils;
import org.orecruncher.sndctrl.SoundControl;

@Mod.EventBusSubscriber(modid = SoundControl.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Keys {

    private static KeyBinding quickVolumeGui;
    private static KeyBinding soundConfigGui;

    public static void register() {
        quickVolumeGui = new KeyBinding(
                "sndctrl.text.quickvolumemenu.open",
                InputMappings.UNKNOWN.getValue(),
                "dsurround.text.controls.group");
        quickVolumeGui.setKeyModifierAndCode(KeyModifier.CONTROL, InputMappings.getKey("key.keyboard.v"));

        soundConfigGui = new KeyBinding(
                "sndctrl.text.soundconfig.open",
                InputMappings.UNKNOWN.getValue(),
                "dsurround.text.controls.group");
        soundConfigGui.setKeyModifierAndCode(KeyModifier.CONTROL, InputMappings.getKey("key.keyboard.i"));

        ClientRegistry.registerKeyBinding(quickVolumeGui);
        ClientRegistry.registerKeyBinding(soundConfigGui);
    }

    @SubscribeEvent
    public static void keyPressed(InputEvent.KeyInputEvent event) {
        if (GameUtils.getMC().screen == null && GameUtils.getPlayer() != null) {
            if (quickVolumeGui.consumeClick()) {
                GameUtils.getMC().setScreen(new QuickVolumeScreen());
            } else if (soundConfigGui.consumeClick()) {
                final boolean singlePlayer = GameUtils.getMC().getCurrentSnooperAction().equals("singleplayer");
                GameUtils.getMC().setScreen(new IndividualSoundControlScreen(null, singlePlayer));
                if (singlePlayer)
                    GameUtils.getMC().getSoundManager().pause();
            }
        }
    }
}