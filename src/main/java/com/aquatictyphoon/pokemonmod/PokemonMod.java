package com.aquatictyphoon.pokemonmod;

import com.aquatictyphoon.pokemonmod.setup.ModSetup;
import com.aquatictyphoon.pokemonmod.setup.ClientSetup;
import com.aquatictyphoon.pokemonmod.setup.events.PokemonDataHandler;
import com.aquatictyphoon.pokemonmod.setup.registration.EntityTypeInit;
import com.aquatictyphoon.pokemonmod.setup.registration.Registration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")

@Mod(PokemonMod.MOD_ID)
public class PokemonMod {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_NAME = "Simply Pokemon";
    public static final java.lang.String MOD_ID = "pokemonmod";


    public PokemonMod() {
        //Register the deferred registry
        Registration.init();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityTypeInit.ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new PokemonDataHandler());

        //Register the setup method for mod loading
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::initRenders);

    }
}
