package com.aquatictyphoon.pokemonmod.setup.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@SuppressWarnings("EntityConstructor")
public class PokemonEntity extends TamableAnimal {
    private static final EntityDataAccessor<Integer> LEVEL =
            SynchedEntityData.defineId(PokemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CATCHRATE =
            SynchedEntityData.defineId(PokemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GENDER =
            SynchedEntityData.defineId(PokemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TYPE_1 =
            SynchedEntityData.defineId(PokemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TYPE_2 =
            SynchedEntityData.defineId(PokemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> UNIQUE_ID =
            SynchedEntityData.defineId(PokemonEntity.class, EntityDataSerializers.INT);


    public PokemonEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEVEL, 1);
        this.entityData.define(CATCHRATE, 0);
        //Gender, 0=male, 1=female, 2=unknown/none, 3=undefined/null
        this.entityData.define(GENDER, 3);
        this.entityData.define(TYPE_1, 0);
        this.entityData.define(TYPE_2, 0);
        this.entityData.define(UNIQUE_ID, 0);
    }

    public int getPokemonLevel() {
        return getEntityData().get(LEVEL);
    }

    public void setPokemonLevel(int level) {
        getEntityData().set(LEVEL, level);
    }

    public int getCatchrate() {
        return getEntityData().get(CATCHRATE);
    }

    public void setCatchrate(int catchrate) {
        getEntityData().set(CATCHRATE, catchrate);
    }

    public int getGender() {
        return getEntityData().get(GENDER);
    }

    public void setGender(int gender)
    {
        getEntityData().set(GENDER, gender);
    }

    public int getType1() {
        return getEntityData().get(TYPE_1);
    }

    public void setType1(int type1) {
        getEntityData().set(TYPE_1, type1);
    }

    public int getType2() {
        return getEntityData().get(TYPE_2);
    }

    public void setType2(int type2) {
        getEntityData().set(TYPE_2, type2);
    }

    public int getUniqueID() {
        return getEntityData().get(UNIQUE_ID);
    }

    public void setUniqueID(int unique_id) {
        getEntityData().set(UNIQUE_ID, unique_id);
    }


}
