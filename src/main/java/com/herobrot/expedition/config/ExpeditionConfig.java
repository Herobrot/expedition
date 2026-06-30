package com.herobrot.expedition.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.ArrayList;
import java.util.List;

@Config(name = "expedition")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class ExpeditionConfig implements ConfigData {
    public float titleSize = 0.5f;
    @ConfigEntry.BoundedDiscrete(min = -200, max = 200)
    public int titleX = 0;
    @ConfigEntry.BoundedDiscrete(min = -200, max = 200)
    public int titleY = -128;

    @ConfigEntry.Gui.Tooltip
    public List<String> excludedBiomes = new ArrayList<>();
}