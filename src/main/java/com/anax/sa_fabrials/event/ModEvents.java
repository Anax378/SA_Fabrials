package com.anax.sa_fabrials.event;

import com.anax.sa_fabrials.util.stormlight.StormlightStorage;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEvents {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(StormlightStorage.class);
    }

}
