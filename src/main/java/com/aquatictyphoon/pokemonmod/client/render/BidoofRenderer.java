package com.aquatictyphoon.pokemonmod.client.render;

import com.aquatictyphoon.pokemonmod.PokemonMod;
import com.aquatictyphoon.pokemonmod.client.models.BidoofModel;
import com.aquatictyphoon.pokemonmod.setup.entities.passive.BidoofEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class BidoofRenderer extends MobRenderer<BidoofEntity, BidoofModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(PokemonMod.MOD_ID, "textures/entity/bidoof.png");

    //In 1.18, we now pass a LAYER_LOCATION (see the explanation in BidoofModel) and bake it in
    //using the renderer's EntityRendererProvider.Context in the entity's renderer and pass it through to the constructor
    public BidoofRenderer(EntityRendererProvider.Context context) {
        super(context, new BidoofModel(context.getModelSet().bakeLayer(BidoofModel.LAYER_LOCATION)), 0.5f);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(BidoofEntity entity) {
        return TEXTURE;
    }
}