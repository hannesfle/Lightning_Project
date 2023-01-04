package net.hannes.lightningenergy.item;

import net.hannes.lightningenergy.LightningEnergy;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LightningEnergy.MOD_ID);

    public static final RegistryObject<Item> LIGHTNING_INGOT = ITEMS.register("lightning_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.LIGHTNING_ENERGY)));
    public static final RegistryObject<Item> ENERGY_INGOT = ITEMS.register("energy_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.LIGHTNING_ENERGY)));

    public  static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
