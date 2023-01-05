package net.hannes.lightningenergy.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightningInfuserBlock extends Block {
    //Blockstate state
    public static final BooleanProperty WORKING = BooleanProperty.create("working");

    public LightningInfuserBlock(Properties properties) {
        super(properties);
    }
    //add override functions here


    //Blockstate Logic


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            level.setBlock(blockPos, state.cycle(WORKING), 3); //3 wer alles notified wird
        }
        return super.use(state, level, blockPos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WORKING);
    }

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
