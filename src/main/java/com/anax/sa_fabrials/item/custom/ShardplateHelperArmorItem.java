package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.SAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.BindingCurseEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShardplateHelperArmorItem extends ArmorItem {
    public ShardplateHelperArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorMaterial, equipmentSlot, properties);
    }
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack itemStack) {
        return false;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!player.getInventory().getArmor(EquipmentSlot.CHEST.getIndex()).is(SAItems.SHARDPLATE_CHESTPLATE.get())){
            stack.shrink(1);
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean b) {
        if(!EnchantmentHelper.hasBindingCurse(itemStack)){itemStack.enchant(Enchantments.BINDING_CURSE, 1);}
        boolean vanish = true;
        for(ItemStack stack : entity.getArmorSlots()){
            if(stack.equals(itemStack)){vanish = false;}
        }
        if(vanish){itemStack.shrink(1);}
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        return null;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag tooltipFlag) {
        itemStack.hideTooltipPart(ItemStack.TooltipPart.ENCHANTMENTS);
    }
}
