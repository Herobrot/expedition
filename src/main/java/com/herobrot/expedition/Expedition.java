package com.herobrot.expedition;

import com.herobrot.expedition.config.ExpeditionConfig;
import com.herobrot.expedition.events.ClientEvents;
import com.herobrot.expedition.network.ExpeditionNetwork;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Expedition.MOD_ID)
public class Expedition {
    public static final String MOD_ID = "expedition";
    public static ExpeditionConfig CONFIG;
    public static boolean isLevelplateLoaded = false;
    public static boolean isScalingDifficultyLoaded = false;

    public Expedition(IEventBus modEventBus, ModContainer modContainer) {
        AutoConfig.register(ExpeditionConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ExpeditionConfig.class).getConfig();
        isLevelplateLoaded = ModList.get().isLoaded("levelplate");
        isScalingDifficultyLoaded = ModList.get().isLoaded("scalingdifficulty");
        ExpeditionNetwork.register(modEventBus);
        if (FMLEnvironment.dist.isClient()) {
            ClientEvents.registerConfigScreen(modContainer);
        }
    }
}