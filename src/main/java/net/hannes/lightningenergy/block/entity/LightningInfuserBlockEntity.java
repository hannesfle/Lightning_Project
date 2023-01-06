package net.hannes.lightningenergy.block.entity;

import net.hannes.lightningenergy.LightningEnergy;
import net.hannes.lightningenergy.item.ModItems;
import net.hannes.lightningenergy.screen.LightningInfuserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LightningInfuserBlockEntity extends BlockEntity implements MenuProvider {
    /* BLOCK INVENTORY HANDLER - 3 SLOTS */
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
           setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty(); //needed to make inventory available via capabilites
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    public LightningInfuserBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.LIGHTNING_INFUSER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> LightningInfuserBlockEntity.this.progress;
                    case 1 -> LightningInfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> LightningInfuserBlockEntity.this.progress = value;
                    case 1 -> LightningInfuserBlockEntity.this.maxProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Lighting Infusing Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LightningInfuserMenu(id, inventory, this, this.data);
    }

    /* ALLOWS IMPORT AND EXPORT ITEMS OUT OF THIS BLOCK ENTITY */
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHandler);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    /* FOR SAVING THE INVENTORY AND LOADING THE INVENTORY*/
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("lightning_infuser.progress", this.progress);
        super.saveAdditional(nbt);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("lightning_infuser.progress");

    }

    /* WHEN BLOCK IS DESTROYED SPILL OUT INVENTORY */
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    /* TICK METHOD GETS CALLED EVERY TICK */
    public static void tick(Level level, BlockPos pos, BlockState state, LightningInfuserBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        if (hasRecipe(pEntity)) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }


    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(LightningInfuserBlockEntity pEntity) {
        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(1,1,false);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.ENERGY_INGOT.get(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            pEntity.resetProgress();
        }

    }

    private static boolean hasRecipe(LightningInfuserBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasLightningIngotinFirstSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.LIGHTNING_INGOT.get();

        return hasLightningIngotinFirstSlot && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, new ItemStack(ModItems.ENERGY_INGOT.get(),1));
    }
    /* METHODS TO CHECK IF OUTPUT SLOT IS AVAILABLE */
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2). getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
