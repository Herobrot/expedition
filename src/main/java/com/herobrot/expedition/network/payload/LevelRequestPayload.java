package com.herobrot.expedition.network.payload;

import com.herobrot.expedition.Expedition;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record LevelRequestPayload() implements CustomPacketPayload {
    public static final Type<LevelRequestPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Expedition.MOD_ID, "level_request"));
    public static final StreamCodec<ByteBuf, LevelRequestPayload> STREAM_CODEC =
            StreamCodec.unit(new LevelRequestPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}