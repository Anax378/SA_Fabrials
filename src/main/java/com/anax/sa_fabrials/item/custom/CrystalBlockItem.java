package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.block.entity.custom.CrystalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrystalBlockItem extends BlockItem {
    public CrystalBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos blockPos, Level level, @Nullable Player player, ItemStack itemStack, BlockState blockState) {
        CrystalBlockEntity entity = (CrystalBlockEntity) level.getBlockEntity(blockPos);
        entity.stormlightStorage.receiveStormlight(itemStack.getOrCreateTag().getInt("stormlight"), false);
        return super.updateCustomBlockEntityTag(blockPos, level, player, itemStack, blockState);
    }
}
