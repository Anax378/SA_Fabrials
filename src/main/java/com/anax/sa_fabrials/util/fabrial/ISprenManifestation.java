package com.anax.sa_fabrials.util.fabrial;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface ISprenManifestation {
    public void targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side);
    public void targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction);
    public int getCost(int power, boolean charge);

    public boolean isUseOnSelf();
    public String getSprenName();

}
