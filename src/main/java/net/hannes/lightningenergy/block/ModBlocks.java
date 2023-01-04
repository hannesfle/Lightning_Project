package net.hannes.lightningenergy.block;

import net.hannes.lightningenergy.LightningEnergy;
import net.hannes.lightningenergy.item.ModCreativeModTab;
import net.hannes.lightningenergy.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LightningEnergy.MOD_ID);
    //Adding Blocks here
    public static final RegistryObject<Block> LIGHTNING_PEDESTAL = registerBlock("lightning_pedestal",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)) ,ModCreativeModTab.LIGHTNING_ENERGY);  //here you can add more block properties

    //function to register block and items
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab );
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab)    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab) ));
    }



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
