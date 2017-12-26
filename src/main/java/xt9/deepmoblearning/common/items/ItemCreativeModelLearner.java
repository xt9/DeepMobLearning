package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.util.KeyboardHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCreativeModelLearner extends ItemBase {
    public ItemCreativeModelLearner() {
        super("creative_model_learner", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nullable EnumHand hand) {
        NonNullList<ItemStack> inv1 = player.inventory.mainInventory;
        NonNullList<ItemStack> inv2 = player.inventory.offHandInventory;

        if(!player.world.isRemote) {
            if(KeyboardHelper.isHoldingShift()) {
                findAndLevelUpModels(inv1, player, true);
                findAndLevelUpModels(inv2, player, true);
            } else if(KeyboardHelper.isHoldingCTRL()) {
                findAndLevelUpModels(inv1, player, false);
                findAndLevelUpModels(inv2, player, false);
            }
        }


        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    public void findAndLevelUpModels(NonNullList<ItemStack> inventory, EntityPlayer player, boolean increaseWholeTier) {
        for(ItemStack inventoryStack : inventory) {
            if (inventoryStack.getItem() instanceof ItemDeepLearner) {
                NonNullList<ItemStack> deepLearnerInternalInv = ItemDeepLearner.getContainedItems(inventoryStack);
                for (ItemStack stack : deepLearnerInternalInv) {
                    if (stack.getItem() instanceof ItemMobChip) {
                        int tier = ItemMobChip.getTier(stack);
                        if(tier != DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
                            if(increaseWholeTier) {
                                ItemMobChip.setTier(stack, (tier + 1));
                            } else {
                                ItemMobChip.increaseMobKillCount(stack, player);
                            }
                        }

                    }
                    ItemDeepLearner.setContainedItems(inventoryStack, deepLearnerInternalInv);
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if(!KeyboardHelper.isHoldingShift()) {
            list.add(I18n.format("deepmoblearning.holdshift"));
        } else {
            list.add("A creative item that levels up data models inside the Deep Learner." );
            list.add("§r§oSHIFT§r§7 + §r§oRIGHT§r§7 click to increase tier.§r" );
            list.add("§r§oCTRL§r§7 + §r§oRIGHT§r§7 click to simulate kills.§r" );
        }

    }
}