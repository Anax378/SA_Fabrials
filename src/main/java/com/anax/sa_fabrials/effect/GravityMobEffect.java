package com.anax.sa_fabrials.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class GravityMobEffect extends MobEffect {

    protected GravityMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
        addAttributeModifier(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get(), "5521735e-261e-40fb-ab5a-35910ad4c244", 0.016D, AttributeModifier.Operation.ADDITION);
    }

}
