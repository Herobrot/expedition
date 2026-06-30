package com.herobrot.expedition.events;

import com.herobrot.expedition.Expedition;
import com.herobrot.expedition.config.ExpeditionConfig;
import com.herobrot.expedition.network.payload.LevelRequestPayload;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Expedition.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    private static ResourceLocation displayedBiomeId = null;
    private static ResourceLocation pendingBiomeId   = null;
    private static int ticksInPendingBiome = 0;
    private static int titleCooldownTicks  = 0;
    private static final int MICRO_BIOME_TOLERANCE = 40;
    private static final int TITLE_ANIMATION_TIME  = 100;

    public static void registerConfigScreen(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parentScreen) ->
                AutoConfig.getConfigScreen(ExpeditionConfig.class, parentScreen).get());
    }

    @SubscribeEvent
    @SuppressWarnings("resource")
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || event.getEntity() != client.player) return;
        if (titleCooldownTicks > 0) {
            titleCooldownTicks--;
        }
        ResourceLocation actualId = client.player.level()
                .getBiome(client.player.blockPosition())
                .unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);
        if (actualId == null) return;
        if (actualId.equals(displayedBiomeId)) {
            pendingBiomeId = null;
            ticksInPendingBiome = 0;
            return;
        }
        if (!actualId.equals(pendingBiomeId)) {
            pendingBiomeId = actualId;
            ticksInPendingBiome = 0;
            return;
        }
        ticksInPendingBiome++;
        if (ticksInPendingBiome < MICRO_BIOME_TOLERANCE) return;
        if (titleCooldownTicks > 0) return;
        displayedBiomeId   = pendingBiomeId;
        pendingBiomeId     = null;
        ticksInPendingBiome = 0;
        titleCooldownTicks = TITLE_ANIMATION_TIME;
        if (Expedition.CONFIG.excludedBiomes.contains(displayedBiomeId.toString())) return;
        client.gui.setTitle(Component.translatable(displayedBiomeId.toLanguageKey("biome")));
        client.gui.setSubtitle(Component.empty());
        if (Expedition.isLevelplateLoaded && Expedition.isScalingDifficultyLoaded) {
            PacketDistributor.sendToServer(new LevelRequestPayload());
        }
    }
}