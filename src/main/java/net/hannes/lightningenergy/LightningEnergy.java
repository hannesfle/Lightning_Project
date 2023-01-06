package net.hannes.lightningenergy;

import com.mojang.logging.LogUtils;
import net.hannes.lightningenergy.block.ModBlocks;
import net.hannes.lightningenergy.block.entity.ModBlockEntities;
import net.hannes.lightningenergy.item.ModItems;
import net.hannes.lightningenergy.networking.ModMessages;
import net.hannes.lightningenergy.screen.LightningInfuserScreen;
import net.hannes.lightningenergy.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LightningEnergy.MOD_ID)
public class LightningEnergy
{
    public static final String MOD_ID = "lightningenergy";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LightningEnergy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);


        modEventBus.addListener(this::commonSetup);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    };

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.LIGHTNING_INFUSER_MENU.get(), LightningInfuserScreen::new);

        }
    }
}
