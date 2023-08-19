package com.anax.sa_fabrials.util.fabrial;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SprenManifestation implements ISprenManifestation{

    SprenManifestation(){};

    @Override
    public boolean isUseOnSelf() {
        return false;
    }

    @Override
    public void targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side) {

    }

    @Override
    public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction) {

    }

    @Override
    public int getCost(int power, boolean charge) {
        return 0;
    }

    @Override
    public String getSprenName() {
        return null;
    }
}
