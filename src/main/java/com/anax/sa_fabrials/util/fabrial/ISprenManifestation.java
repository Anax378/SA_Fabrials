package com.anax.sa_fabrials.util.fabrial;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface ISprenManifestation {
    public int targetBlock(Position pos, Level level, int power, boolean charge, Vec3 direction, Direction side, boolean simulate);
    public int targetEntity(LivingEntity entity, Level level, int power, boolean charge, Vec3 direction, boolean simulate);
    public boolean isUseOnSelf();
    public String getSprenName();

}
