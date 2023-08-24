package com.anax.sa_fabrials;

import com.anax.sa_fabrials.item.SAItems;
import com.anax.sa_fabrials.item.custom.ToggleFabrialItem;
import com.anax.sa_fabrials.util.fabrial.FabrialEffects;
import com.google.common.collect.Iterables;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.function.Predicate;

public class SAEventHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){

    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof LightningBolt){
            List<Entity> entities = event.getLevel().getEntities(entity, entity.getBoundingBox().inflate(6), new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    return entity instanceof LivingEntity;
                }
            });
            boolean doBreak = false;
            for(Entity levelEntity : entities){
                Iterable<ItemStack> slots = levelEntity.getAllSlots();
                if(levelEntity instanceof Player){
                    slots = Iterables.concat(slots, ((Player)levelEntity).getInventory().items);
                }
                for(ItemStack stack : slots){
                    if(stack.getItem() instanceof ToggleFabrialItem){
                        if(stack.getOrCreateTag().getBoolean("is_on") && !(stack.getOrCreateTag().getBoolean("charge")) && stack.getOrCreateTag().getString("spren").equals(FabrialEffects.LIGHTNING_MANIFESTATION.getSprenName())){
                            int power = stack.getOrCreateTag().getInt("power");
                            if(levelEntity.distanceTo(entity) > power*10){return;}
                            int stormlight = stack.getOrCreateTag().getInt("stormlight");
                            int stormlightNeeded = FabrialEffects.targetEntity(event.getEntity(), event.getLevel(), power, false, levelEntity.getLookAngle(), FabrialEffects.LIGHTNING_MANIFESTATION.getSprenName(), false, true);
                            if(stormlight > stormlightNeeded){
                                event.setCanceled(true);
                                stack.getOrCreateTag().putInt("stormlight", stormlight-stormlightNeeded);
                                doBreak = true;
                                break;
                            }
                        }
                    }
                }
                if(doBreak){break;}
            }
        }

    }


}
