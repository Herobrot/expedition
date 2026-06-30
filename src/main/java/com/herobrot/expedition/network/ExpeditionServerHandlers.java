package com.herobrot.expedition.network;

import com.herobrot.expedition.Expedition;
import com.herobrot.expedition.compat.ScalingDifficultyIntegration;
import com.herobrot.expedition.network.payload.LevelRequestPayload;
import com.herobrot.expedition.network.payload.LevelSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ExpeditionServerHandlers {
    public static void handleLevelRequest(LevelRequestPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            if (!Expedition.isLevelplateLoaded || !Expedition.isScalingDifficultyLoaded) return;
            int calculatedLevel = ScalingDifficultyIntegration.getAreaAverageLevel(player.serverLevel(), player.blockPosition());
            PacketDistributor.sendToPlayer(player, new LevelSyncPayload(calculatedLevel));
        });
    }
}