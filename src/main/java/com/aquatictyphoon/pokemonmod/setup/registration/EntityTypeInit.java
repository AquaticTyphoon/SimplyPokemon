package com.aquatictyphoon.pokemonmod.setup.registration;

import com.aquatictyphoon.pokemonmod.PokemonMod;
import com.aquatictyphoon.pokemonmod.setup.entities.Pokeball_Entity;
import com.aquatictyphoon.pokemonmod.setup.entities.passive.BidoofEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//Separate init for entities for organization
public class EntityTypeInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, PokemonMod.MOD_ID);

    //Projectiles
    public static final RegistryObject<EntityType<Pokeball_Entity>> POKE_BALL = ENTITIES.register("poke_ball",
            () -> EntityType.Builder.<Pokeball_Entity>of(Pokeball_Entity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).build("poke_ball"));

    public static final RegistryObject<EntityType<BidoofEntity>> BIDOOF = ENTITIES.register("bidoof",
            () -> EntityType.Builder.<BidoofEntity>of(BidoofEntity::new, MobCategory.CREATURE)
                    .sized(1F, 1F).build("bidoof"));

}
