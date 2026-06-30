package com.herobrot.expedition.network.payload;

import com.herobrot.expedition.Expedition;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record LevelSyncPayload(int level) implements CustomPacketPayload {
    public static final Type<LevelSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Expedition.MOD_ID, "level_sync"));
    public static final StreamCodec<ByteBuf, LevelSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, LevelSyncPayload::level,
                    LevelSyncPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}