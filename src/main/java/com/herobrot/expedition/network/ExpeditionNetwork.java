package com.herobrot.expedition.network;

import com.herobrot.expedition.network.payload.LevelRequestPayload;
import com.herobrot.expedition.network.payload.LevelSyncPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class ExpeditionNetwork {
    private static final String PROTOCOL_VERSION = "1";
    private ExpeditionNetwork() {}
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ExpeditionNetwork::onRegisterPayloads);
    }
    private static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(LevelRequestPayload.TYPE, LevelRequestPayload.STREAM_CODEC,
                ExpeditionServerHandlers::handleLevelRequest);
        registrar.playToClient(LevelSyncPayload.TYPE, LevelSyncPayload.STREAM_CODEC,
                ExpeditionClientHandlers::handleLevelSync);
    }
}