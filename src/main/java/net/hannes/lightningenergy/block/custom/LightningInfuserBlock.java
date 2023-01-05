package net.hannes.lightningenergy.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightningInfuserBlock extends Block {
    public LightningInfuserBlock(Properties properties) {
        super(properties);
    }
    //add override functions here

    //Tooltip Logic for Blocks
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Can simulate Lightning with the Power of RF!").withStyle(ChatFormatting.AQUA));
        }   else{
           components.add(Component.literal("Press SHIFT for more Info").withStyle(ChatFormatting.YELLOW));
        }


        super.appendHoverText(stack, blockGetter, components, flag);
    }
}
