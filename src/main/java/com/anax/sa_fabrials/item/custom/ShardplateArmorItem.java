package com.anax.sa_fabrials.item.custom;

import com.anax.sa_fabrials.item.ItemStormlightStorageProvider;
import com.anax.sa_fabrials.item.SAItems;
import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.BindingCurseEnchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShardplateArmorItem extends ArmorItem {
    public ShardplateArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorMaterial, equipmentSlot, properties);
    }
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack itemStack) {
        return false;
    }


    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(stack.is(SAItems.SHARDPLATE_CHESTPLATE.get())){
            if(!player.getInventory().getArmor(EquipmentSlot.HEAD.getIndex()).is(SAItems.SHARDPLATE_HELMET.get())){
                player.drop(player.getInventory().getArmor(EquipmentSlot.HEAD.getIndex()).copy(), true);
                player.setItemSlot(EquipmentSlot.HEAD, SAItems.SHARDPLATE_HELMET.get().getDefaultInstance());
            }
            if(!player.getInventory().getArmor(EquipmentSlot.LEGS.getIndex()).is(SAItems.SHARDPLATE_LEGGINGS.get())){
                player.drop(player.getInventory().getArmor(EquipmentSlot.LEGS.getIndex()).copy(), true);
                player.setItemSlot(EquipmentSlot.LEGS, SAItems.SHARDPLATE_LEGGINGS.get().getDefaultInstance());
            }
            if(!player.getInventory().getArmor(EquipmentSlot.FEET.getIndex()).is(SAItems.SHARDPLATE_BOOTS.get())){
                player.drop(player.getInventory().getArmor(EquipmentSlot.FEET.getIndex()).copy(), true);
                player.setItemSlot(EquipmentSlot.FEET, SAItems.SHARDPLATE_BOOTS.get().getDefaultInstance());
            }
        }
        else{

        }
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return super.canEquip(stack, armorType, entity);
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        int[] storedAndMaxStormlight = {0, 0};

        if(!itemStack.getOrCreateTag().contains("power")){itemStack.getOrCreateTag().putInt("power", 1);}
        if(!itemStack.getOrCreateTag().contains("is_attractor")){itemStack.getOrCreateTag().putBoolean("is_attractor", true);}

        itemStack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {storedAndMaxStormlight[0] = handler.getStormlightStored();storedAndMaxStormlight[1] = handler.getMaxStormlightStored();});
        componentList.add(new TranslatableComponent("tooltip.sa_fabrials.stored_stormlight").append(" " + Integer.toString(storedAndMaxStormlight[0]) + "/" + Integer.toString(storedAndMaxStormlight[1])));

    }
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemStormlightStorageProvider(10000, 10000, 10000, 0, stack);
    }

    public float damageAfterAbsorption(int damageToAbsorb, ItemStack stack){
        int[] extracted = {0};
        stack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {
            extracted[0] = handler.extractStormlight(damageToAbsorb, false);
        });
        return damageToAbsorb-extracted[0];

    }

    public boolean caAbsorbAll(int damageToAbsorb, ItemStack stack){
        int[] extracted = {0};
        stack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {
            extracted[0] = handler.extractStormlight(damageToAbsorb, true);
        });
        if (extracted[0] == damageToAbsorb){
            stack.getCapability(StormlightStorage.STORMLIGHT_STORAGE).ifPresent(handler -> {
                handler.extractStormlight(extracted[0], false);
            });
            return true;
        }
        return false;

    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
