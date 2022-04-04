package com.aquatictyphoon.pokemonmod.setup.advanced.items;

import com.aquatictyphoon.pokemonmod.setup.entities.Pokeball_Entity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import javax.annotation.Nonnull;
import java.util.List;

public class PokeballItem extends Item {
    public PokeballItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(@Nonnull() ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (containsEntity(stack)) {
            if (!getID(stack).isEmpty()) {
                String s0 = "entity." + getID(stack);
                String s1 = s0.replace(':', '.');
            }
            if (getID(stack).isEmpty()) {
                tooltip.add(new TranslatableComponent("tooltip.pokemonmod.item.pokeball"));
            }
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public TextComponent getName(@Nonnull ItemStack stack) {
        if (!containsEntity(stack))
            return new TextComponent(I18n.get("item.pokemonmod.pokeball"));
        else{
            String s0 = "entity." + getID(stack);
            String s1 = s0.replace(':','.');
            return new TextComponent(I18n.get("item.pokemonmod.pokeball") +": "+ I18n.get(s1));}
    }

    //Helper
    public static boolean containsEntity(@Nonnull ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("entity");
    }

    public static String getID(ItemStack stack) {
        return stack.getOrCreateTag().getString("entity");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        pPlayer.getCooldowns().addCooldown(this, 10);
        ItemStack PokeballItem = pPlayer.getItemInHand(pHand);
        if ((!pLevel.isClientSide) && !(getID(pPlayer.getItemInHand(pHand)).isEmpty())) {
            Pokeball_Entity projectile = new Pokeball_Entity(pPlayer, pLevel, pHand);
            projectile.setItem(PokeballItem);
            projectile.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F,  0.8F, 1.0F);
            pLevel.addFreshEntity(projectile);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if ((!pLevel.isClientSide) && (getID(pPlayer.getItemInHand(pHand)).isEmpty())){
            Pokeball_Entity projectile = new Pokeball_Entity(pPlayer, pLevel, pHand);
            projectile.setItem(PokeballItem);
            projectile.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(projectile);
            PokeballItem.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(PokeballItem, pLevel.isClientSide());

    }

}