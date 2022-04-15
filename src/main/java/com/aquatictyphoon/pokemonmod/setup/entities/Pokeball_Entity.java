package com.aquatictyphoon.pokemonmod.setup.entities;

import com.aquatictyphoon.pokemonmod.setup.Registration;
import jdk.jfr.Event;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Objects;
import java.util.Random;

import static com.aquatictyphoon.pokemonmod.setup.Registration.POKEBALL;
import static com.aquatictyphoon.pokemonmod.setup.Registration.POKE_BALL;

public class Pokeball_Entity extends ThrowableItemProjectile {

    public Pokeball_Entity(EntityType<Pokeball_Entity> type, Level level) {
        super(type, level);
    }

    public Pokeball_Entity(LivingEntity entity, Level level, InteractionHand pHand) {
        super(POKE_BALL.get(), entity, level);
        this.setOwner(entity);
        PokeballItem = entity.getItemInHand(pHand);
    }

    private static ItemStack PokeballItem = ItemStack.EMPTY;


    protected @NotNull Item getDefaultItem() {
        return (POKEBALL.get());
    }

    public boolean containsEntity(@Nonnull ItemStack PokeballItem) {
        if (!PokeballItem.hasTag()) return false;
        assert PokeballItem.getTag() != null;
        return PokeballItem.getTag().contains("entity");
    }

    public Entity getEntityFromNBT(@Nonnull ItemStack PokeballItem, Level world, boolean withInfo) {
        assert PokeballItem.getTag() != null;
        Entity entity = Objects.requireNonNull(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(PokeballItem.getTag().getString("entity")))).create(world);
        if (withInfo) {
            assert entity != null;
            entity.load(PokeballItem.getTag());
        }
        return entity;
    }

    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (result.getType() == EntityHitResult.Type.ENTITY && (!(result.getEntity() instanceof Player)) && ((result.getEntity() instanceof TamableAnimal))){
            if (!level.isClientSide) {
                if ((containsEntity(PokeballItem)) && (target.isAlive() && ((TamableAnimal) target).isTame()) && !(target.getPersistentData().getDouble("CaughtID") == PokeballItem.getOrCreateTag().getDouble("CaughtID"))) {
                    Entity entity = getEntityFromNBT(PokeballItem, target.level, true);
                    BlockPos blockPos = this.blockPosition();
                    entity.absMoveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
                    level.addFreshEntity(entity);
                    //System.out.println("RELEASE SUCCESS!");
                }
                if ((!containsEntity(PokeballItem)) && (target.isAlive() && !((TamableAnimal) target).isTame()))
                {
                    Player player = (Player) this.getOwner();
                    if(player == null){
                        return;
                    }
                    ((TamableAnimal) target).tame((Player) this.getOwner());
                    CompoundTag nbt = new CompoundTag();
                    String entityID = EntityType.getKey(target.getType()).toString();

                    Random random = new Random();
                    Integer CaughtID = random.nextInt(999999)+1;

                    nbt.putDouble("CaughtID", CaughtID);
                    nbt.putString("entity", entityID);
                    nbt.putString("id", EntityType.getKey(target.getType()).toString());
                    target.save(nbt);
                    ItemStack owned_ball = new ItemStack(Registration.POKEBALL.get());
                    owned_ball.setTag(nbt);
                    ItemEntity ball_drop = new ItemEntity(target.level, target.getX(), target.getY(), target.getZ(), owned_ball);
                    level.addFreshEntity(ball_drop);
                    target.discard();
                    //System.out.println("CAPTURE SUCCESS!");
                }
                //Retrieval
                if(((containsEntity(PokeballItem)) && (target.isAlive() && ((TamableAnimal) target).isTame())) && (target.getPersistentData().getDouble("CaughtID") == PokeballItem.getOrCreateTag().getDouble("CaughtID")))
                {
                    Player player = (Player) this.getOwner();
                    if(player == null){
                        return;
                    }

                    System.out.println("RECAPTURE SUCCESS!");
                    //player.displayClientMessage(new TranslatableComponent("pokeball.cannot.catch"), true);
                }
            }
        }
//        if (result.getType() == EntityHitResult.Type.ENTITY && ((result.getEntity() instanceof Player))){
//            if((containsEntity(PokeballItem))){
//                //Need to program battles
//            }
//            else{
//                //Do nothing
//            }
//        }

    }

    protected void onHitBlock(@NotNull BlockHitResult result) {
        if (!level.isClientSide) {
            if (containsEntity(PokeballItem)) {
                Player player = (Player) this.getOwner();
                if (player == null) {
                    return;
                }
                Entity entity = getEntityFromNBT(PokeballItem, player.level, true);
                BlockPos blockPos = this.blockPosition();
                entity.absMoveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
                level.addFreshEntity(entity);
                //System.out.println("RELEASE SUCCESS!");
                this.discard();
            }
            else {
                this.discard();
            }
        }
    }
}