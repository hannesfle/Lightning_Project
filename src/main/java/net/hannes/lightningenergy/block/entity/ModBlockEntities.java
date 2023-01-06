package net.hannes.lightningenergy.block.entity;

import net.hannes.lightningenergy.LightningEnergy;
import net.hannes.lightningenergy.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities  {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LightningEnergy.MOD_ID);

    /* REGISTERING THE BLOCK ENTITY AND SAYING IT BELONGS TO THIS BLOCK */
    public static final RegistryObject<BlockEntityType<LightningInfuserBlockEntity>> LIGHTNING_INFUSER =
        BLOCK_ENTITIES.register("lightning_infuser", ()->
            BlockEntityType.Builder.of(LightningInfuserBlockEntity::new,
                    ModBlocks.LIGHTNING_INFUSER.get()).build(null));
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
