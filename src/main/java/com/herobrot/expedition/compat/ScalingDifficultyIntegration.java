package com.herobrot.expedition.compat;

import com.herobrot.scalingdifficulty.api.DifficultyCalculator;
import com.herobrot.scalingdifficulty.compat.LevelplateCompat;
import com.herobrot.scalingdifficulty.data.DimensionDifficultyLoader;
import com.herobrot.scalingdifficulty.data.DimensionSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class ScalingDifficultyIntegration {
    public static int getAreaAverageLevel(ServerLevel level, BlockPos pos) {
        float rawMultiplier = DifficultyCalculator.calculateRawMultiplier(level, pos, false);
        DimensionSettings settings = DimensionDifficultyLoader.getSettings(level.dimension().location().toString());
        float cappedMultiplier = Math.min(rawMultiplier, (float) settings.maxFactorHealth);
        return LevelplateCompat.getLevelFromMultiplier(cappedMultiplier);
    }
}