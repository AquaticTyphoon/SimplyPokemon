package com.aquatictyphoon.pokemonmod.setup.entities.passive;

import com.aquatictyphoon.pokemonmod.setup.entities.PassivePokemonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BidoofEntity extends PassivePokemonEntity {
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int jumpDelayTicks;

    //Goals and attributes is what make the basics of a living entity, along with a movement and/or jump controller
    //Flying mobs have a flying controller instead of movement controller as well
    public BidoofEntity(EntityType<? extends PassivePokemonEntity> type, Level worldIn) {
        super(type, worldIn);
        this.jumpControl = new BidoofEntity.BidoofJumpControl(this);
        this.moveControl = new MoveControl(this);
        this.setTame(false);
    }

    //RandomSwimmingGoal is only used by Dolphins and Fish mobs
    //Most mobs avoid water and use WaterAvoidingRandomWalkingGoal instead of RandomWalkingGoal
    //The only exception are Polar Bears, Drowned and humanoid Illager mobs
    //Most passive-animal type mobs also use PanicGoal along with BreedGoal and TemptGoal
    //Most hostile mobs use an AttackGoal and/or TargetGoal
    protected void registerGoals() {
        //Be careful with modifying the numbers in the goals
        //You may cause unintended erratic mob behavior if you don't know what you're doing
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 17.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    //Dedicated spawn rules, useful if you want more complex spawning rules, 1.18 now uses BlockTags
    public static boolean checkPokemonSpawnRules(EntityType<BidoofEntity> p_29699_, LevelAccessor p_29700_, MobSpawnType p_29701_, BlockPos p_29702_, Random p_29703_) {
        return p_29700_.getBlockState(p_29702_.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
    }

    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(17.0D);
            this.setHealth(17.0F);
        }
    }

    //Animation stuff, remove this if you want//
    protected float getJumpPower() {
        if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5D))) {
            Path path = this.navigation.getPath();
            if (path != null && !path.isDone()) {
                Vec3 vec3 = path.getNextEntityPos(this);
                if (vec3.y > this.getY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveControl.getSpeedModifier() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    protected void jumpFromGround() {
        super.jumpFromGround();
        double d0 = this.moveControl.getSpeedModifier();
        if (d0 > 0.0D) {
            double d1 = this.getDeltaMovement().horizontalDistanceSqr();
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)1);
        }

    }

    public float getJumpCompletion(float p_29736_) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + p_29736_) / (float)this.jumpDuration;
    }

    public void setJumping(boolean p_29732_) {
        super.setJumping(p_29732_);
        if (p_29732_) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    public void customServerAiStep() {
        if (this.jumpDelayTicks > 0) {
            --this.jumpDelayTicks;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            BidoofEntity.BidoofJumpControl bidoof$bidoofjumpcontrol = (BidoofEntity.BidoofJumpControl)this.jumpControl;
            if (!bidoof$bidoofjumpcontrol.wantJump()) {
                if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
                    Path path = this.navigation.getPath();
                    Vec3 vec3 = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
                    if (path != null && !path.isDone()) {
                        vec3 = path.getNextEntityPos(this);
                    }

                    this.facePoint(vec3.x, vec3.z);
                    this.startJumping();
                }
            } else if (!bidoof$bidoofjumpcontrol.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    private void facePoint(double p_29687_, double p_29688_) {
        this.setYRot((float)(Mth.atan2(p_29688_ - this.getZ(), p_29687_ - this.getX()) * (double)(180F / (float)Math.PI)) - 90.0F);
    }

    private void enableJumpControl() {
        ((BidoofEntity.BidoofJumpControl)this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((BidoofEntity.BidoofJumpControl)this.jumpControl).setCanJump(false);
    }

    private void setLandingDelay() {
        if (this.moveControl.getSpeedModifier() < 2.2D) {
            this.jumpDelayTicks = 10;
        } else {
            this.jumpDelayTicks = 1;
        }

    }

    private void checkLandingDelay() {
        this.setLandingDelay();
        this.disableJumpControl();
    }

    public void aiStep() {
        super.aiStep();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }

    }

    public void handleEntityEvent(byte p_29663_) {
        if (p_29663_ == 1) {
            this.spawnSprintParticle();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleEntityEvent(p_29663_);
        }

    }

    public static class BidoofJumpControl extends JumpControl {
        private final BidoofEntity bidoof;
        private boolean canJump;

        public BidoofJumpControl(BidoofEntity p_186229_) {
            super(p_186229_);
            this.bidoof = p_186229_;
        }

        public boolean wantJump() {
            return this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean p_29759_) {
            this.canJump = p_29759_;
        }

        public void tick() {
            if (this.jump) {
                this.bidoof.startJumping();
                this.jump = false;
            }

        }
    }
    //

    protected SoundEvent getJumpSound() {
        return SoundEvents.RABBIT_JUMP;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_29715_) {
        return SoundEvents.RABBIT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }
}

