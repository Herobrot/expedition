package com.herobrot.expedition.events;

import com.herobrot.expedition.Expedition;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Expedition.MOD_ID, value = Dist.CLIENT)
public class ClientRenderEvents {

    @SubscribeEvent
    public static void onPreRenderTitle(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(VanillaGuiLayers.TITLE)) {
            GuiGraphics graphics = event.getGuiGraphics();
            graphics.pose().pushPose();
            float scale = Expedition.CONFIG.titleSize;
            int x = Expedition.CONFIG.titleX;
            int y = Expedition.CONFIG.titleY;
            int screenWidth = graphics.guiWidth();
            int screenHeight = graphics.guiHeight();
            graphics.pose().translate(screenWidth / 2f + x, screenHeight / 2f + y, 0);
            graphics.pose().scale(scale, scale, 1.0f);
            graphics.pose().translate(-screenWidth / 2f, -screenHeight / 2f, 0);
        }
    }

    @SubscribeEvent
    public static void onPostRenderTitle(RenderGuiLayerEvent.Post event) {
        if (event.getName().equals(VanillaGuiLayers.TITLE)) {
            event.getGuiGraphics().pose().popPose();
        }
    }
}