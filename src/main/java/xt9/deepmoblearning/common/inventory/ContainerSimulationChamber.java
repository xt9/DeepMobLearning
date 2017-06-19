package xt9.deepmoblearning.common.inventory;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.handlers.SimulationChamberHandler;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;

/**
 * Created by xt9 on 2017-06-17.
 */
public class ContainerSimulationChamber extends Container {
    public DeepEnergyStorage energyStorage;
    public SimulationChamberHandler handler;
    public TileEntitySimulationChamber tile;
    public EntityPlayer player;
    public World world;

    public ContainerSimulationChamber(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        this.player = inventory.player;
        this.world = world;
        this.tile = te;

        this.handler = (SimulationChamberHandler) this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.energyStorage = (DeepEnergyStorage) this.tile.getCapability(CapabilityEnergy.ENERGY, null);

        this.addSlotsToHandler();
        this.addInventorySlots();
    }


    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        if(!this.world.isRemote) {
            // Update the tile every other tick while container is open
            this.intervalUpdate(2);
        }
    }

    private void intervalUpdate(int divisor) {
        if(this.tile.ticks % divisor == 0) {
            IBlockState state = this.world.getBlockState(this.tile.getPos());

            if(this.tile.energy != this.energyStorage.getEnergyStored()) {
                this.tile.energy = this.energyStorage.getEnergyStored();
                this.world.notifyBlockUpdate(this.tile.getPos(), state, state, 3);
            } else if(this.tile.isCrafting) {
                this.world.notifyBlockUpdate(this.tile.getPos(), state, state, 3);
            }
        }
    }

    private void addSlotsToHandler() {
        this.addSlotToContainer(new SlotSimulationChamber(this.handler, DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT, -13, 1));
        this.addSlotToContainer(new SlotSimulationChamber(this.handler, DeepConstants.SIMULATION_CHAMBER_INPUT_SLOT, 174, 17));
        this.addSlotToContainer(new SlotSimulationChamber(this.handler, DeepConstants.SIMULATION_CHAMBER_OUTPUT_SLOT, 198, 17));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(this.player.inventory, index, 36 + row * 18, 211);
            this.addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 36 + column * 18;
                int y = 153 + row * 18;
                int index = column + row * 9 + 9;
                Slot slot = new Slot(this.player.inventory, index, x, y);
                this.addSlotToContainer(slot);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        this.tile.markDirty();
        this.player.inventory.markDirty();
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !this.player.isSpectator();
    }
}
