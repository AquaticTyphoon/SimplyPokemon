package com.aquatictyphoon.pokemonmod.setup.events;

import com.aquatictyphoon.pokemonmod.setup.entities.passive.BidoofEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;



public class PokemonDataHandler {
    Random rand = new Random();
    //next int counts from zero and ignores the last number so you will only get possible results of 0-4 so keep this in mind

    @SubscribeEvent
    public void addPokeData(EntityJoinWorldEvent event) {
        Random rand = new Random();
        if (event.getEntity() instanceof BidoofEntity) {
            ((BidoofEntity) event.getEntity()).setCatchrate(255);
            ((BidoofEntity) event.getEntity()).setUniqueID(rand.nextInt(1000000));

        }
        //SEPARATOR
    }



}


