package com.herobrot.expedition.network;

import com.herobrot.expedition.network.payload.LevelSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ExpeditionClientHandlers {
    public static void handleLevelSync(LevelSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().player == null) return;
            int mobLevel = payload.level();
            Minecraft.getInstance().gui.setSubtitle(Component.translatable("hud.expedition.subtitle", mobLevel));
        });
    }
}