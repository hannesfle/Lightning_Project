package net.hannes.lightningenergy.event;

import net.hannes.lightningenergy.LightningEnergy;
import net.hannes.lightningenergy.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = LightningEnergy.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) { // here you can make stuff happen
            if(KeyBinding.DEBUG.consumeClick())
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Debug Text"));
        }

    }

    @Mod.EventBusSubscriber(modid = LightningEnergy.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DEBUG); //here you register the keys

        }
    }
}
