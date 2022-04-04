package com.aquatictyphoon.pokemonmod.setup;

import com.aquatictyphoon.pokemonmod.PokemonMod;
import com.aquatictyphoon.pokemonmod.setup.registration.EntityTypeInit;
import com.aquatictyphoon.pokemonmod.setup.registration.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PokemonMod.MOD_ID)
public class SimplyPokemonWorldGen {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getName().equals(new ResourceLocation("minecraft:plains")) ||
                event.getName().equals(new ResourceLocation("minecraft:sunflower_plains")) ||
                event.getName().equals(new ResourceLocation("minecraft:swamp")) ||
                event.getName().equals(new ResourceLocation("minecraft:forest")) ||
                event.getName().equals(new ResourceLocation("minecraft:flower_forest")) ||
                event.getName().equals(new ResourceLocation("minecraft:birch_forest")) ||
                event.getName().equals(new ResourceLocation("minecraft:old_growth_birch_forest")) ||
                event.getName().equals(new ResourceLocation("minecraft:meadow")) ||
                event.getName().equals(new ResourceLocation("minecraft:river"))) {
            event.getSpawns().addSpawn(MobCategory.CREATURE,
                    new MobSpawnSettings.SpawnerData(EntityTypeInit.BIDOOF.get(), 50, 1, 3));
        }
    }
}
