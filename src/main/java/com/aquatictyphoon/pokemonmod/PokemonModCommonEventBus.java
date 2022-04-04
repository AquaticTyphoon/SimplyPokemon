package com.aquatictyphoon.pokemonmod;

import com.aquatictyphoon.pokemonmod.setup.registration.EntityTypeInit;
import com.aquatictyphoon.pokemonmod.setup.registration.Registration;
import com.aquatictyphoon.pokemonmod.setup.entities.passive.BidoofEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = PokemonMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PokemonModCommonEventBus {

    @SubscribeEvent
    public static void onStaticCommonSetup(FMLCommonSetupEvent event) {
        SpawnPlacements.register(EntityTypeInit.BIDOOF.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE, BidoofEntity::checkPokemonSpawnRules);
    }

    //This method is required to give your entity its attributes, not doing so will cause MC to fail to load
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.BIDOOF.get(), BidoofEntity.customAttributes().build());
    }


}

