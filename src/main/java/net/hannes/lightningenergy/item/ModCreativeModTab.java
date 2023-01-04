package net.hannes.lightningenergy.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab {
    public static final CreativeModeTab LIGHTNING_ENERGY = new CreativeModeTab("lightningenergytab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.LIGHTNING_INGOT.get());
        }
    };
}
